package com.nicolashurtado.messagingapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.nicolashurtado.messagingapp.loader.DataLoader
import com.nicolashurtado.messagingapp.db.daos.AttachmentDao
import com.nicolashurtado.messagingapp.db.daos.MessageDao
import com.nicolashurtado.messagingapp.db.daos.UserDao
import com.nicolashurtado.messagingapp.db.entities.Attachment
import com.nicolashurtado.messagingapp.db.entities.Message
import com.nicolashurtado.messagingapp.db.entities.User
import com.nicolashurtado.messagingapp.loader.models.Chat


@Database(entities = [User::class, Message::class, Attachment::class], version = 1, exportSchema = false)
abstract class MessagingDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun attachmentDao(): AttachmentDao

    companion object : SingletonHolder<MessagingDatabase, Context>({
        Room.inMemoryDatabaseBuilder(it.applicationContext, MessagingDatabase::class.java).build()
    })

    fun loadDataFromFile(context: Context, gson: Gson, fileName: String) {
        runInTransaction {
            val chat = DataLoader<Chat>(gson).loadData(context, fileName, Chat::class.java)
            chat?.users?.let {
                userDao().insertAll(*(it.map { user ->
                    User(user.id, user.name, user.avatarId)
                }.toTypedArray()))
            }

            chat?.messages?.let {
                messageDao().insertAll(*(it.map { message ->
                    Message(message.id, message.userId, message.content)
                }.toTypedArray()))
            }

            val attachments = ArrayList<Attachment>()
            chat?.messages?.forEach { message ->
                attachments.addAll(message.attachments.map { attachment ->
                    Attachment(attachment.id, message.id, attachment.thumbnailUrl, attachment.title, attachment.url)
                })
            }

            attachmentDao().insertAll(*attachments.toTypedArray())
        }

    }
}