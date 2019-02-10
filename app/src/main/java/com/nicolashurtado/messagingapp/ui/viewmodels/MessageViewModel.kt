package com.nicolashurtado.messagingapp.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.google.gson.Gson
import com.nicolashurtado.messagingapp.db.MessagingDatabase
import com.nicolashurtado.messagingapp.db.entities.Message
import com.nicolashurtado.messagingapp.db.entities.Publication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel(private val database: MessagingDatabase, private val gson: Gson) : ViewModel() {


    private val publicationsLiveData: LiveData<PagedList<Publication>> by lazy {
        val factory: DataSource.Factory<Int, Publication> = database.publicationDao().getAllPaged()

        val pagedListBuilder: LivePagedListBuilder<Int, Publication> =
                LivePagedListBuilder<Int, Publication>(factory, 5)
        pagedListBuilder.build()
    }

    fun loadData(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            database.loadDataFromFile(context, gson, "data.json")
        }
    }

    fun getPublications(): LiveData<PagedList<Publication>> {
        return publicationsLiveData
    }
}