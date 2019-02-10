package com.nicolashurtado.messagingapp.daos

import androidx.room.Dao
import androidx.room.Insert
import com.nicolashurtado.messagingapp.models.Attachment
import com.nicolashurtado.messagingapp.models.User

@Dao
interface AttachmentDao {
    @Insert
    fun insertAll(vararg attachments: Attachment)
}