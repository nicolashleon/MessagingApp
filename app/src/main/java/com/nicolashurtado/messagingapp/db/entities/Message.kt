package com.nicolashurtado.messagingapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = User::class, childColumns = ["user_id"],
        parentColumns = ["id"], onDelete = ForeignKey.CASCADE)])
data class Message(@PrimaryKey var id: Int, @ColumnInfo(name = "user_id") var userId: Int,
                   var content: String)