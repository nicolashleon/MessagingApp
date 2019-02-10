package com.nicolashurtado.messagingapp.loader.models

data class Message(val id: Int, val userId: Int, val content: String,
                   val attachments: List<Attachment>)