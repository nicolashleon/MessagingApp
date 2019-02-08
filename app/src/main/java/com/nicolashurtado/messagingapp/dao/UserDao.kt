package com.nicolashurtado.messagingapp.dao

import androidx.room.Dao
import androidx.room.Insert
import com.nicolashurtado.messagingapp.models.User

@Dao
interface UserDao {
    @Insert
    fun insertAll(vararg users: User)
}