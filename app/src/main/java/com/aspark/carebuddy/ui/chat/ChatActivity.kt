package com.aspark.carebuddy.ui.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.aspark.carebuddy.chat.ChatMessage
import com.aspark.carebuddy.model.Nurse
import com.aspark.carebuddy.ui.chat.ui.theme.CareBuddyTheme
import dagger.hilt.android.AndroidEntryPoint
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import javax.inject.Inject

@AndroidEntryPoint
class ChatActivity : ComponentActivity() {

    private val viewModel: ChatActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var list = arrayListOf("Hi", "Hello", "how are you?", "i m fine",
            "How are you doing ", "Thanks for asking",
            "Hi", "Hello", "how are you?", "i m fine",
            "How are you doing ", "Thanks for asking",
            "Hi", "Hello", "how are you?", "i m fine",
            "How are you doing ", "Thanks for asking",
            "Hi", "Hello", "how are you? This is a big message " +
                    "just for the sake of testing . Do not panic." +
                    "Keep calm and find the nearest exit", "i m fine",
            "How are you doing ", "Thanks for asking",
            "Hi", "Hello", "how are you?", "i m fine",
            "How are you doing ", "Thanks for asking",
            "DO NOT TEXT ME")

        val messagesList = arrayListOf<MessageData>()

        for ((count,i) in list.withIndex()) {
            val c = count %2==0
            messagesList.add(MessageData(i, c,""))
        }

        val nurse = Nurse()
        nurse.firstName = "John"
        nurse.lastName = "Doe"

        val chatMessage = viewModel.getChatMessage()

        setContent {
            CareBuddyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatScreen(
                        nurse = nurse, messageData = messagesList,
                        chatMessage, this ,{ finish() }) {

                        viewModel.sendMessage(it)
                    }
                }
            }
        }
    }
}
