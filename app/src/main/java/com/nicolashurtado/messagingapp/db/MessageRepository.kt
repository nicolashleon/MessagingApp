package com.nicolashurtado.messagingapp.db

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nicolashurtado.messagingapp.db.daos.AttachmentDao
import com.nicolashurtado.messagingapp.db.daos.MessageDao
import com.nicolashurtado.messagingapp.db.daos.PublicationDao
import com.nicolashurtado.messagingapp.db.daos.SeedDao
import com.nicolashurtado.messagingapp.db.entities.Attachment
import com.nicolashurtado.messagingapp.db.entities.Message
import com.nicolashurtado.messagingapp.db.entities.Publication
import com.nicolashurtado.messagingapp.db.entities.User
import com.nicolashurtado.messagingapp.loader.DataLoader
import com.nicolashurtado.messagingapp.loader.models.Chat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageRepository(private val attachmentDao: AttachmentDao,
                        private val publicationDao: PublicationDao,
                        private val messageDao: MessageDao,
                        private val seedDao: SeedDao,
                        private val dataLoader: DataLoader) {

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
                LivePagedListBuilder<Int, Publication>(publicationDao.getAllPaged(), pageConfig.build())
        pagedListBuilder.build()
    }

    fun loadData(fileName: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val chat = dataLoader.loadData(fileName, Chat::class.java)

            val users = chat?.users?.map { user ->
                User(user.id, user.name, user.avatarId)
            }.orEmpty()

            val messages = chat?.messages?.map { message ->
                Message(message.id, message.userId, message.content)
            }.orEmpty()

            val attachments = ArrayList<Attachment>()
            chat?.messages?.forEach { message ->
                if (message.attachments != null) {
                    attachments.addAll(message.attachments.map { attachment ->
                        Attachment(attachment.id, message.id, attachment.thumbnailUrl, attachment.title, attachment.url)
                    })
                }
            }
            seedDao.loadDatabase(users, messages, attachments)
        }
    }

    fun getAllPublicationsPaged(): LiveData<PagedList<Publication>> {
        return publicationsLiveData
    }

    fun deleteMessage(message: Message) {
        CoroutineScope(Dispatchers.IO).launch {
            messageDao.delete(message)
        }
    }

    fun deleteAttachment(attachment: Attachment) {
        CoroutineScope(Dispatchers.IO).launch {
            attachmentDao.delete(attachment)
        }
    }
}