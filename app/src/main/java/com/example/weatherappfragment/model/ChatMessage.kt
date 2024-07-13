package com.example.weatherappfragment.model

data class ChatMessage(
    val senderID: String = "",
    val content: String = "",
    val timestamp: Long = 0L
)

data class Comment(
    val senderID: String = "",
    val content: String = "",
    val timestamp: Long = 0L,
    var commentId: String = ""
)