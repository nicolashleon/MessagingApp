package com.nicolashurtado.messagingapp

import android.app.Application
import com.google.gson.GsonBuilder
import com.nicolashurtado.messagingapp.BuildConfig.DATA_SEED_FILE_NAME
import com.nicolashurtado.messagingapp.db.MessageRepository
import com.nicolashurtado.messagingapp.db.MessagingDatabase
import com.nicolashurtado.messagingapp.loader.DataLoader
import com.nicolashurtado.messagingapp.ui.viewmodels.MessageViewModel
import org.koin.android.ext.android.get
import org.koin.android.ext.android.startKoin
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class MessagingApplication : Application() {

    private val appModule by lazy {
        module {
            single {
                this@MessagingApplication
            }
            single {
                GsonBuilder().create()
            }
            single {
                DataLoader(get(), get())
            }
            single {
                MessagingDatabase.getInstance(this@MessagingApplication)
            }
            single {
                val db: MessagingDatabase = get()
                MessageRepository(db.attachmentDao(), db.publicationDao(), db.messageDao(),
                        db.seedDao(), get())
            }
            viewModel { MessageViewModel(get()) }
        }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule))

        val repository: MessageRepository = get()
        repository.loadData(DATA_SEED_FILE_NAME)
    }
}