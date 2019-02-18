package com.nicolashurtado.messagingapp.db

import androidx.paging.PagedList
import com.nicolashurtado.messagingapp.BuildConfig
import com.nicolashurtado.messagingapp.db.daos.SeedDao
import com.nicolashurtado.messagingapp.db.entities.Attachment
import com.nicolashurtado.messagingapp.db.entities.Message
import com.nicolashurtado.messagingapp.db.entities.Publication
import com.nicolashurtado.messagingapp.db.entities.User
import com.nicolashurtado.messagingapp.loader.DataLoader
import com.nicolashurtado.messagingapp.loader.models.Chat
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PublicationBoundaryCallback(private val dataLoader: DataLoader, private val seedDao: SeedDao) :
        PagedList.BoundaryCallback<Publication>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        loadData(BuildConfig.DATA_SEED_FILE_NAME)
    }

    /**
     * This function loads a file into the database.
     * This action :
     *
     * 1. It is intended for development only. Having a bad data file will cause the database to show
     * no results and print an stack trace with the error. It is not a good idea to ship an application
     * to the users with a corrupted file for this point.
     * 2. Will not throw an exception to avoid crashing/interfering the application for final users or devs
     * 3. Will not trigger any alerts/errors to the user because there are not any recover actions for them to take.
     */
    private fun loadData(fileName: String) {
        CoroutineScope(Dispatchers.IO).launch(CoroutineExceptionHandler { coroutineContext: CoroutineContext, throwable: Throwable ->
            println(throwable.printStackTrace())
        }) {

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
}