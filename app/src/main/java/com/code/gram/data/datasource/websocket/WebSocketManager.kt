package com.code.gram.data.datasource.websocket

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import timber.log.Timber

class WebSocketManager {

    private val json = Json { ignoreUnknownKeys = true }
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    private var messageChannel: Channel<String>? = null
    val messages: Flow<String>
        get() = messageChannel?.receiveAsFlow()
            ?: throw IllegalStateException("WebSocket not connected")

    @OptIn(DelicateCoroutinesApi::class)
    fun connect(url: String, code : String) {
        // 이전 연결 정리
        webSocket?.close(1000, "Reconnecting")
        messageChannel?.close()

        messageChannel = Channel(Channel.BUFFERED)
        val fixedUrl = addSchemeIfMissing(url.lowercase())
        val request = Request.Builder().url(fixedUrl).build()
        Timber.e("connectcode $code")

        val jsonPayload = buildJsonObject {
            put("code", code)
        }.toString()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Timber.d("WebSocket Opened. Sending initial code payload...")
                // 연결이 열리자마자 초기 메시지(코드) 전송
                webSocket.send(jsonPayload)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                messageChannel?.trySend(text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                messageChannel?.trySend("{\"type\":\"error\", \"data\":\"${t.message}\"}")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                messageChannel?.trySend("{\"type\":\"status\", \"data\":\"CLOSED\"}")
            }
        })

        messageChannel?.invokeOnClose { webSocket?.close(1000, "Client closed") }
    }

    fun send(input: String) {
        webSocket?.send(input)
    }

    fun close() {
        webSocket?.close(1000, "Client closed")
    }

    private fun addSchemeIfMissing(url: String): String {
        return "ws://$url"
    }
}