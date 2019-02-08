package com.nicolashurtado.messagingapp.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nicolashurtado.messagingapp.models.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM Message")
    fun getAllPaged(): DataSource.Factory<Int, Message>

    @Insert
    fun insertAll(vararg messages: Message)
}