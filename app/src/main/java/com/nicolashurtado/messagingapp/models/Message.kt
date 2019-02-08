package com.nicolashurtado.messagingapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
        @PrimaryKey val id: Int,
        val userId: Int,
        val attachments: List<Attachment>?,
        val content: String
)