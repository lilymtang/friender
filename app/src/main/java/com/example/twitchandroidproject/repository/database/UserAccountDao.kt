package com.example.twitchandroidproject.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.twitchandroidproject.repository.model.UserAccount

@Dao
interface UserAccountDao {

    @Insert
    suspend fun create(userAccount: UserAccount)

    @Query("SELECT COUNT(*) FROM UserAccount WHERE email = :email")
    suspend fun getCountByEmail(email: String): Int

    @Query("SELECT * FROM UserAccount WHERE email = :email")
    suspend fun getByEmail(email: String): UserAccount?

}