package com.nicolashurtado.messagingapp.db.daos

import androidx.room.Dao
import androidx.room.Transaction
import com.nicolashurtado.messagingapp.db.MessagingDatabase
import com.nicolashurtado.messagingapp.db.entities.Attachment
import com.nicolashurtado.messagingapp.db.entities.Message
import com.nicolashurtado.messagingapp.db.entities.User

@Dao
abstract class SeedDao(private val db : MessagingDatabase) {

    @Transaction
    open fun loadDatabase(users: List<User>, messages : List<Message>, attachments: List<Attachment>) {
        db.userDao().insertAll(*users.toTypedArray())
        db.messageDao().insertAll(*messages.toTypedArray())
        db.attachmentDao().insertAll(*attachments.toTypedArray())
    }
}