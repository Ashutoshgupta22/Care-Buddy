package com.aspark.carebuddy.ui.chat

data class MessageData(
    val content: String,
    val sentByMe: Boolean,
    val timeStamp: String
)
