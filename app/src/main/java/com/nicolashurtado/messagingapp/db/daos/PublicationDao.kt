package com.nicolashurtado.messagingapp.db.daos

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.nicolashurtado.messagingapp.db.entities.Publication

@Dao
interface PublicationDao {
    @Transaction
    @Query("SELECT * from Message, User WHERE Message.user_id == User.id")
    fun getAllPaged(): DataSource.Factory<Int, Publication>
}