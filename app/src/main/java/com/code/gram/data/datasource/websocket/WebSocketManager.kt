package com.code.gram.data.datasource.websocket

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketManager {

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    private val messageChannel = Channel<String>(Channel.Factory.BUFFERED)
    val messages: Flow<String> = messageChannel.receiveAsFlow()

    @OptIn(DelicateCoroutinesApi::class)
    fun connect(url: String) {
        val fixedUrl = addSchemeIfMissing(url)
        val request = Request.Builder().url(fixedUrl).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                messageChannel.trySend(text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                messageChannel.trySend("{\"type\":\"error\", \"data\":\"${t.message}\"}")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                messageChannel.trySend("{\"type\":\"status\", \"data\":\"CLOSED\"}")
            }
        })

        kotlinx.coroutines.GlobalScope.launch {
            messageChannel.invokeOnClose { webSocket?.close(1000, "Client closed") }
        }
    }

    fun send(input: String) {
        webSocket?.send(input)
    }

    fun close() {
        webSocket?.close(1000, "Client closed")
    }

    private fun addSchemeIfMissing(url: String): String {
        return if (url.startsWith("ws://") || url.startsWith("wss://") ||
            url.startsWith("http://") || url.startsWith("https://")) {
            url
        } else {
            // 로컬 테스트 기본값 ws://
            "ws://$url"
        }
    }
}