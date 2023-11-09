package com.aspark.carebuddy.websocket

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

class WebSocketService {

    private val client = OkHttpClient.Builder()
        .readTimeout(3,TimeUnit.SECONDS)
        .build()

    fun connectWebSocket(url: String, listener: WebSocketListener): WebSocket {

        val request = Request.Builder()
            .url(url)
            .build()

        return client.newWebSocket(request, listener)
    }
}