package com.nicolashurtado.messagingapp.models

import androidx.room.ColumnInfo

data class Attachment(
        @ColumnInfo(name = "attachment_id") val id: String,
        @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "url") val url: String)