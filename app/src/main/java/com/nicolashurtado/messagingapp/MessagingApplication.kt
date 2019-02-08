package com.nicolashurtado.messagingapp

import android.app.Application
import androidx.work.WorkerParameters
import com.google.gson.GsonBuilder
import com.nicolashurtado.messagingapp.db.MessagingDatabase
import com.nicolashurtado.messagingapp.db.SeedDatabaseWorker
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.module

class MessagingApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val module = module {
            single {
                this@MessagingApplication
            }
            single {
                GsonBuilder().create()
            }
            single {
                MessagingDatabase.buildDatabase(this@MessagingApplication)
            }
        }
        startKoin(this, listOf(module))
    }
}