package com.nicolashurtado.messagingapp.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class Publication(@Embedded val user: User,
                  @Embedded val message : Message,
                  @Relation(parentColumn = "message_id", entityColumn = "message_id") val attachments: List<Attachment>)