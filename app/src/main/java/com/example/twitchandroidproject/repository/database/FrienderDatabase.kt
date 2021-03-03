package com.example.twitchandroidproject.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.twitchandroidproject.repository.model.UserProfile

@Database(entities = [UserProfile::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseTypeConverters::class)
abstract class FrienderDatabase : RoomDatabase() {

    abstract fun userProfileDao(): UserProfileDao
}