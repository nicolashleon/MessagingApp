package com.nicolashurtado.messagingapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.nicolashurtado.messagingapp.dao.MessageDao
import com.nicolashurtado.messagingapp.dao.UserDao
import com.nicolashurtado.messagingapp.models.Message
import com.nicolashurtado.messagingapp.models.User

@Database(entities = [User::class, Message::class], version = 1, exportSchema = false)
abstract class MessagingDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao

    companion object {
        private const val DATABASE_NAME = "Messaging"

        fun buildDatabase(context: Context): MessagingDatabase {
            return Room.databaseBuilder(context, MessagingDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequest.Builder(SeedDatabaseWorker::class.java).build()
                            WorkManager.getInstance().enqueue(request)
                        }
                    }).build()
        }
    }
}