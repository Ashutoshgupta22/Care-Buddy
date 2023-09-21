package com.aspark.carebuddy.ui.chat

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.aspark.carebuddy.model.Nurse
import com.aspark.carebuddy.ui.chat.ui.theme.CareBuddyTheme

class ChatActivity : ComponentActivity() {
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

        val messagesList = arrayListOf<Message>()

        for ((count,i) in list.withIndex()) {

            val c = count %2==0
            messagesList.add(Message(i, c,""))

        }

        val nurse = Nurse()
        nurse.firstName = "John"
        nurse.lastName = "Doe"

        setContent {
            CareBuddyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatScreen(nurse = nurse, messages = messagesList,{
                        finish()
                    }) {

                    }
                }
            }
        }
    }
}
