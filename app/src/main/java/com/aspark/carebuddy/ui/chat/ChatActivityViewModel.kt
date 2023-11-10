package com.aspark.carebuddy.ui.chat

import androidx.lifecycle.ViewModel
import com.aspark.carebuddy.chat.ChatMessage
import com.aspark.carebuddy.websocket.MyWebSocketListener
import com.aspark.carebuddy.websocket.WebSocketService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import javax.inject.Inject

@HiltViewModel
class ChatActivityViewModel @Inject constructor(
    private val chatMessage: ChatMessage): ViewModel() {


//    fun sendMessage(message: Message) {
//
//        val url = "ws://192.168.1.10:8080/care-buddy-websocket"
//
//        val webSocket = webSocketService.connectWebSocket(url, webSocketListener)
//
//        webSocket.send(message.content)
//
//        Log.i("ChatActivityViewModel", "sendMessage: message sent")
//
//    }

        @OptIn(DelicateCoroutinesApi::class)
        fun sendMessage(messageData: MessageData) {

            GlobalScope.launch(Dispatchers.IO) {
                chatMessage.sendMessage(messageData)
            }
        }


}