package com.nicolashurtado.messagingapp

import android.app.Application
import com.google.gson.GsonBuilder
import com.nicolashurtado.messagingapp.db.MessagingDatabase
import com.nicolashurtado.messagingapp.ui.viewmodels.MessageViewModel
import org.koin.android.ext.android.startKoin
import org.koin.androidx.viewmodel.ext.koin.viewModel
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
                MessagingDatabase.getInstance(this@MessagingApplication)
            }
            viewModel { MessageViewModel(get(), get()) }
        }
        startKoin(this, listOf(module))

    }
}