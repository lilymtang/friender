package com.example.twitchandroidproject.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.twitchandroidproject.repository.model.UserProfile
import io.reactivex.rxjava3.core.Flowable

@Dao
interface UserProfileDao {

    @Query("SELECT * FROM UserProfile")
    fun getUsers(): Flowable<List<UserProfile>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserProfile>)
}