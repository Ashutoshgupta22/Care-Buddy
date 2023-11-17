package com.aspark.carebuddy.ui.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aspark.carebuddy.chat.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jxmpp.jid.impl.JidCreate
import javax.inject.Inject

@HiltViewModel
class ChatActivityViewModel @Inject constructor(
    private val connection: XMPPTCPConnection
) : ViewModel() {

//    private val _receivedMessage = MutableLiveData<MessageData>()
//    val receivedMessage: LiveData<MessageData> = _receivedMessage

    @OptIn(DelicateCoroutinesApi::class)
    fun sendMessage(messageData: MessageData) {

        GlobalScope.launch(Dispatchers.IO) {
            ChatMessage(connection).sendMessage(messageData)
        }
    }

    fun getChatMessage(): ChatMessage {

        return ChatMessage(connection)
    }

//    private fun send(messageData: MessageData) {
//
//        if (connection.isConnected) {
//            Log.i("XMPP Connection", "Connection is already established")
//
//            val chatManager = ChatManager.getInstanceFor(connection)
//            val jid =
//                JidCreate.entityBareFrom("nurse24@aspark-care-buddy.ap-south-1.elasticbeanstalk.com")
//            val chat = chatManager.chatWith(jid)
//
//            chat.send(messageData.content)
//            Log.i("Message", "sendMessage: Message sent")
//        } else {
//            Log.e("XMPP Connection", "Connection not established $connection")
//        }
//    }

//    fun receiveMessage() {
//
//        val chatManager = ChatManager.getInstanceFor(connection)
//        chatManager.addIncomingListener { from, message, chat ->
//
//            _receivedMessage.postValue(
//                MessageData(message.body, false, "")
//            )
//            Log.i("ViewModel", "receiveMessage: from $from -${message.body}")
//        }
//    }
}