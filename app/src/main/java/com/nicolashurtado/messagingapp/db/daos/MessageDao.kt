package com.nicolashurtado.messagingapp.db.daos

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nicolashurtado.messagingapp.db.entities.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM Message")
    fun getAllPaged(): DataSource.Factory<Int, Message>

    @Insert
    fun insertAll(vararg messages: Message)

    @Delete
    fun delete(message : Message)
}