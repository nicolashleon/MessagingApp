package com.nicolashurtado.messagingapp.db.daos

import androidx.room.Dao
import androidx.room.Insert
import com.nicolashurtado.messagingapp.db.entities.Attachment

@Dao
interface AttachmentDao {
    @Insert
    fun insertAll(vararg attachments: Attachment)
}