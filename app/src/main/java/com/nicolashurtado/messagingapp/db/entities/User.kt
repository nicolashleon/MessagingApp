package com.nicolashurtado.messagingapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey val id: Int,
                @ColumnInfo(name = "name") val name: String,
                @ColumnInfo(name = "avatar_id") val avatarId: String)