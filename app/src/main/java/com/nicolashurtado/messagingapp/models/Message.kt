package com.nicolashurtado.messagingapp.models

data class Message(
    val attachments: List<Attachment>,
    val content: String,
    val id: Int,
    val userId: Int
)