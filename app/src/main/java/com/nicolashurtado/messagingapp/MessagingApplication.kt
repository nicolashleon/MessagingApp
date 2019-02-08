package com.nicolashurtado.messagingapp

import android.app.Application
import com.google.gson.GsonBuilder
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.module

class MessagingApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val module = module {
            single {
                GsonBuilder().create()
            }
        }
        startKoin(this, listOf(module))
    }
}