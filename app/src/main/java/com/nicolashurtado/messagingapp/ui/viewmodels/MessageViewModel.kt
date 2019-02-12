package com.nicolashurtado.messagingapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.nicolashurtado.messagingapp.db.MessageRepository
import com.nicolashurtado.messagingapp.db.entities.Attachment
import com.nicolashurtado.messagingapp.db.entities.Message
import com.nicolashurtado.messagingapp.db.entities.Publication

class MessageViewModel(private val repository: MessageRepository) : ViewModel() {

    fun loadData(fileName: String) {
        repository.loadData(fileName)
    }

    fun deleteMessage(message: Message) {
        repository.deleteMessage(message)
    }

    fun deleteAttachment(attachment: Attachment) {
        repository.deleteAttachment(attachment)
    }

    fun getPublications(): LiveData<PagedList<Publication>> {
        return repository.getAllPublicationsPaged()
    }
}