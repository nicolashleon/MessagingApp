package com.nicolashurtado.messagingapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nicolashurtado.messagingapp.db.MessagingDatabase
import com.nicolashurtado.messagingapp.db.entities.Publication
import com.nicolashurtado.messagingapp.loader.DataLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel(private val database: MessagingDatabase, private val dataLoader: DataLoader) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
        private const val INITIAL_LOAD_SIZE = 20
        private const val DATA_PREFETCH_DISTANCE = 5

        /**
         * This is the formula used by the page list configuration.
         * An error will be thrown if the PAGE_SIZE is less than PAGE_SIZE + 2*DATA_PREFETCH_DISTANCE
         */
        private const val PAGE_MAX_SIZE = PAGE_SIZE + 2 * DATA_PREFETCH_DISTANCE
    }

    private val publicationsLiveData: LiveData<PagedList<Publication>> by lazy {

        val pageConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(INITIAL_LOAD_SIZE)
                .setMaxSize(PAGE_MAX_SIZE)
                .setPageSize(PAGE_SIZE)
                .setPrefetchDistance(DATA_PREFETCH_DISTANCE)

        val pagedListBuilder: LivePagedListBuilder<Int, Publication> =
                LivePagedListBuilder<Int, Publication>(database.publicationDao().getAllPaged(), pageConfig.build())
        pagedListBuilder.build()
    }

    fun loadData(fileName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            database.loadDataFromFile(dataLoader, fileName)
        }
    }

    fun getPublications(): LiveData<PagedList<Publication>> {
        return publicationsLiveData
    }
}