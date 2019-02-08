package com.nicolashurtado.messagingapp.db

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.nicolashurtado.messagingapp.DataLoader
import com.nicolashurtado.messagingapp.models.Chat

class SeedDatabaseWorker(private val context: Context,
                         private val gson: Gson,
                         private val database: MessagingDatabase,
                         workerParams: WorkerParameters) : Worker(context, workerParams) {


    override fun doWork(): Result {

        return try {
            val chat = DataLoader<Chat>(gson).loadData(context, "data.json", Chat::class.java)
            chat?.users?.let {
                database.userDao().insertAll(*it.toTypedArray())
            }
            chat?.messages?.let {
                database.messageDao().insertAll(*it.toTypedArray())
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }


    }

}