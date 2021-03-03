package com.example.twitchandroidproject.di

import android.content.Context
import androidx.room.Room
import com.example.twitchandroidproject.repository.database.FrienderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AndroidTestMainModule {

    @Provides
    @Named("testDatabase")
    fun provideUserDatabase(@ApplicationContext applicationContext: Context): FrienderDatabase {
        return Room.inMemoryDatabaseBuilder(
            applicationContext,
            FrienderDatabase::class.java,
        )
            .allowMainThreadQueries()
            .build()
    }
}