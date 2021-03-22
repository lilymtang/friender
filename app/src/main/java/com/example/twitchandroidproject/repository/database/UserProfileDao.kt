package com.example.twitchandroidproject.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.example.twitchandroidproject.repository.model.UserProfile
import io.reactivex.rxjava3.core.Flowable

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserProfile>)

    @Query("SELECT * FROM UserProfile WHERE userProfileType IN (:userProfileTypes)")
    fun getAll(userProfileTypes: List<UserProfile.UserProfileType>): Flowable<List<UserProfile>>

    @Query("SELECT COUNT(*) FROM UserProfile")
    suspend fun getCount(): Int

    @Query("SELECT * FROM UserProfile WHERE id = :id")
    fun getById(id: Long): Flowable<UserProfile>

    @Query("SELECT * FROM UserProfile WHERE userProfileType IN (:userProfileTypes)" +
        "AND (bio LIKE '%' || :keyword || '%' " +
        "OR preferredInterests LIKE '%' || :keyword || '%' " +
        "OR interests LIKE '%' || :keyword || '%' " +
        "OR fullName LIKE '%' || :keyword || '%')")
    fun getUsersByKeyword(keyword: String, userProfileTypes: List<UserProfile.UserProfileType>): Flowable<List<UserProfile>>

    @Query("SELECT * FROM UserProfile WHERE userProfileType = :userProfileType AND email = :email")
    fun getByProfileTypeAndEmail(
        userProfileType: UserProfile.UserProfileType,
        email: String
    ): Flowable<UserProfile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserProfile(user: UserProfile)

    @Query("DELETE FROM UserProfile WHERE email = :email")
    suspend fun deleteByEmail(email: String)

    @Insert
    suspend fun createUserProfile(user: UserProfile)

}