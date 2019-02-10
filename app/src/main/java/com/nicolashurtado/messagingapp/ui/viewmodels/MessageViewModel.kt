package com.nicolashurtado.messagingapp.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.google.gson.Gson
import com.nicolashurtado.messagingapp.db.MessagingDatabase
import com.nicolashurtado.messagingapp.models.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class MessageViewModel(private val database: MessagingDatabase, private val gson: Gson) : ViewModel() {


    private val messagesLiveData: LiveData<PagedList<Message>> by lazy {
        val factory: DataSource.Factory<Int, Message> = database.messageDao().getAllPaged()

        val pagedListBuilder: LivePagedListBuilder<Int, Message> = LivePagedListBuilder<Int, Message>(
            factory,
            50
        )
        pagedListBuilder.build()
    }

    fun loadData(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            database.loadDataFromFile(context, gson, "data.json")
        }
    }

    fun getMessages(): LiveData<PagedList<Message>> {
        return messagesLiveData
    }
}