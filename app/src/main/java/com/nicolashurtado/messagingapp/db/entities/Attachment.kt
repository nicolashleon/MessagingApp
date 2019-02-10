package com.nicolashurtado.messagingapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(primaryKeys = ["id", "message_id"], foreignKeys = [ForeignKey(entity = Message::class,
        childColumns = ["message_id"], parentColumns = ["id"], onDelete = CASCADE)])
data class Attachment(val id: String,
                      @ColumnInfo(name = "message_id") val messageId: Int,
                      @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String,
                      @ColumnInfo(name = "title") val title: String,
                      @ColumnInfo(name = "url") val url: String)