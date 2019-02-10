package com.nicolashurtado.messagingapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.nicolashurtado.messagingapp.DataLoader
import com.nicolashurtado.messagingapp.daos.MessageDao
import com.nicolashurtado.messagingapp.daos.UserDao
import com.nicolashurtado.messagingapp.models.Attachment
import com.nicolashurtado.messagingapp.models.Chat
import com.nicolashurtado.messagingapp.models.Message
import com.nicolashurtado.messagingapp.models.User

@Database(entities = [User::class, Message::class], version = 1, exportSchema = false)
abstract class MessagingDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao

    companion object : SingletonHolder<MessagingDatabase, Context>({
        Room.inMemoryDatabaseBuilder(it.applicationContext, MessagingDatabase::class.java).build()
    })

    fun loadDataFromFile(context: Context, gson: Gson, fileName: String) {
        runInTransaction {
            val chat = DataLoader<Chat>(gson).loadData(context, fileName, Chat::class.java)
            chat?.users?.let {
                userDao().insertAll(*it.toTypedArray())
            }
            chat?.messages?.let {
                messageDao().insertAll(*it.toTypedArray())
            }
            val attachments = ArrayList<Attachment>()
            chat?.messages?.forEach {
                attachments.addAll(it.attachments)
            }
        }

    }
}