package com.nicolashurtado.messagingapp.db.daos

import androidx.room.Dao
import androidx.room.Insert
import com.nicolashurtado.messagingapp.db.entities.User

@Dao
interface UserDao {
    @Insert
    fun insertAll(vararg users: User)
}