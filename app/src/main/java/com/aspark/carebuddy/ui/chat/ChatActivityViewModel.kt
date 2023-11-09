package com.aspark.carebuddy.ui.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import com.aspark.carebuddy.websocket.MyWebSocketListener
import com.aspark.carebuddy.websocket.WebSocketService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatActivityViewModel @Inject constructor(
    private val webSocketService: WebSocketService,
    private val webSocketListener: MyWebSocketListener ): ViewModel() {


    fun sendMessage(message: Message) {

        val url = "ws://192.168.1.10:8080/care-buddy-websocket"

        val webSocket = webSocketService.connectWebSocket(url, webSocketListener)

        webSocket.send(message.content)

        Log.i("ChatActivityViewModel", "sendMessage: message sent")

    }


}