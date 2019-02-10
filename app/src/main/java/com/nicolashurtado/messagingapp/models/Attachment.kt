package com.nicolashurtado.messagingapp.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Attachment(@PrimaryKey val id: String,
                      @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String,
                      @ColumnInfo(name = "title") val title: String,
                      @ColumnInfo(name = "url") val url: String,
                      )