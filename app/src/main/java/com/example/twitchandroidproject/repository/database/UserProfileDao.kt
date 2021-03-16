package com.example.twitchandroidproject.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.twitchandroidproject.repository.model.UserProfile
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserProfile>)

    @Query("SELECT * FROM UserProfile WHERE userProfileType IN (:userProfileTypes)")
    fun getAll(userProfileTypes: List<UserProfile.UserProfileType>): Observable<List<UserProfile>>

    @Query("SELECT COUNT(*) FROM UserProfile")
    suspend fun getCount(): Int

    @Query("SELECT * FROM UserProfile WHERE id = :id")
    fun getById(id: Long): Flowable<UserProfile>

    @Query("SELECT * FROM UserProfile WHERE email = :email")
    suspend fun getByEmail(email: String): UserProfile

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserProfile(user: UserProfile)

    @Query("DELETE FROM UserProfile WHERE email = :email")
    suspend fun deleteByEmail(email: String)

    @Insert
    suspend fun createUserProfile(user: UserProfile)

}