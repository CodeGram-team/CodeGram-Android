package com.code.gram.presentation.challenge.detail.component

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MathDocumentViewer(
    text: String,
    modifier: Modifier = Modifier
) {
    // HTML 템플릿 (KaTeX CDN 포함)
    val htmlTemplate = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet"
                  href="https://cdn.jsdelivr.net/npm/katex@0.16.9/dist/katex.min.css"
                  crossorigin="anonymous">
            <script defer src="https://cdn.jsdelivr.net/npm/katex@0.16.9/dist/katex.min.js"
                    crossorigin="anonymous"></script>
            <script defer src="https://cdn.jsdelivr.net/npm/katex@0.16.9/dist/contrib/auto-render.min.js"
                    crossorigin="anonymous"></script>
            <style>
                body {
                    font-family: system-ui, sans-serif;
                    font-size: 16px;
                    padding: 12px;
                    margin: 0;
                    background-color: #121212; /* 다크테마 대응 */
                    color: #FFFFFF;
                    line-height: 1.6;
                    word-break: break-word;
                }
                p {
                    margin-bottom: 1em;
                }
                .katex-display {
                    margin: 1em 0;
                    text-align: center;
                }
            </style>
        </head>
        <body>
            ${text
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\n\n", "</p><p>")
        .replace("\n", "<br>")
        .let { "<p>$it</p>" }
    }

            <script>
                document.addEventListener("DOMContentLoaded", function() {
                    renderMathInElement(document.body, {
                        delimiters: [
                            {left: "$$", right: "$$", display: true},
                            {left: "$", right: "$", display: false},
                            {left: "\\(", right: "\\)", display: false},
                            {left: "\\[", right: "\\]", display: true}
                        ]
                    });
                });
            </script>
        </body>
        </html>
    """.trimIndent()

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                isVerticalScrollBarEnabled = false
                isHorizontalScrollBarEnabled = false
                setBackgroundColor(0x00000000)
            }
        },
        update = { webView ->
            webView.loadDataWithBaseURL(
                null,
                htmlTemplate,
                "text/html",
                "UTF-8",
                null
            )
        },
        modifier = modifier
    )
}