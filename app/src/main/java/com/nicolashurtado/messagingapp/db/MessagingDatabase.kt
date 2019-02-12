package com.nicolashurtado.messagingapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.nicolashurtado.messagingapp.db.daos.*
import com.nicolashurtado.messagingapp.db.entities.Attachment
import com.nicolashurtado.messagingapp.db.entities.Message
import com.nicolashurtado.messagingapp.db.entities.User
import com.nicolashurtado.messagingapp.loader.DataLoader
import com.nicolashurtado.messagingapp.loader.models.Chat


@Database(entities = [User::class, Message::class, Attachment::class], version = 1, exportSchema = false)
abstract class MessagingDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun attachmentDao(): AttachmentDao
    abstract fun publicationDao(): PublicationDao
    abstract fun seedDao(): SeedDao

    companion object : SingletonHolder<MessagingDatabase, Context>({
        Room.inMemoryDatabaseBuilder(it.applicationContext, MessagingDatabase::class.java).build()
    })
}