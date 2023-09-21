package com.aspark.carebuddy.ui.chat

data class Message(
    val content: String,
    val sentByMe: Boolean,
    val timeStamp: String
)
