package com.nicolashurtado.messagingapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.nicolashurtado.messagingapp.db.MessageRepository
import com.nicolashurtado.messagingapp.db.entities.Attachment
import com.nicolashurtado.messagingapp.db.entities.Message
import com.nicolashurtado.messagingapp.db.entities.Publication
import com.nicolashurtado.messagingapp.ui.errors.Error
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MessageViewModel(private val repository: MessageRepository) : ViewModel() {

    private val job = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val errorMutableLiveData = MutableLiveData<Error>()
    private val loaderLiveData = MutableLiveData<Boolean>()

    fun deleteMessage(message: Message) {
        uiScope.launch(CoroutineExceptionHandler { coroutineContext: CoroutineContext, throwable: Throwable ->
            errorMutableLiveData.postValue(Error.MessageDeleteError(throwable))
        }) {
            withContext(Dispatchers.IO) {
                repository.deleteMessage(message)
            }
        }
    }

    fun deleteAttachment(attachment: Attachment) {
        uiScope.launch(CoroutineExceptionHandler { coroutineContext: CoroutineContext, throwable: Throwable ->
            errorMutableLiveData.postValue(Error.AttachmentDeleteError(throwable))
        }) {
            withContext(Dispatchers.IO) {
                repository.deleteAttachment(attachment)
            }
        }
    }

    fun getPublications(): LiveData<PagedList<Publication>> {
        return repository.getAllPublicationsPaged()
    }

    fun getErrorData(): LiveData<Error> {
        return errorMutableLiveData
    }

    override fun onCleared() {
        uiScope.coroutineContext.cancelChildren()
        super.onCleared()
    }
}