package com.nicolashurtado.messagingapp.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Message(@PrimaryKey var id: Int,
                   var userId: Int,
                   var content: String,
                   @Ignore var attachments: List<Attachment>) {
    constructor() : this(0,0, String(), ArrayList<Attachment>())
}