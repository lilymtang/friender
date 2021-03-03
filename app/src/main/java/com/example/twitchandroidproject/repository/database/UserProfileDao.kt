package com.example.twitchandroidproject.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.twitchandroidproject.repository.model.UserProfile
import io.reactivex.rxjava3.core.Flowable

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserProfile>)

    @Query("SELECT * FROM UserProfile")
    fun getAll(): Flowable<List<UserProfile>>

    @Query("SELECT * FROM UserProfile WHERE email = :email")
    suspend fun getByEmail(email: String): UserProfile

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrUpdate(user: UserProfile)

    @Query("DELETE FROM UserProfile WHERE email = :email")
    suspend fun deleteByEmail(email: String)
}