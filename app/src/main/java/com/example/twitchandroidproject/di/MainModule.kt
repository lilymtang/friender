package com.example.twitchandroidproject.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.twitchandroidproject.repository.TestDataUtil
import com.example.twitchandroidproject.repository.api.GeolocationApiService
import com.example.twitchandroidproject.repository.database.FrienderDatabase
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

// DI: module containing provider methods for dependencies
@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext applicationContext: Context): FrienderDatabase {
        return Room.databaseBuilder(
            applicationContext,
            FrienderDatabase::class.java,
            "friender_database"
        )
            // wipe database in case of conflicts
            // todo: remove when database model is stabilized
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                // on create prepopulate database with test data
                override fun onCreate(database: SupportSQLiteDatabase) {
                    super.onCreate(database)

                    val userProfileDao = (database as FrienderDatabase).userProfileDao()

                    GlobalScope.launch {
                        val userProfileCount = userProfileDao.getCount()
                        if (userProfileCount == 0) {
                            val userProfiles = TestDataUtil.createInitialUserProfiles()

                            userProfileDao.insertAll(userProfiles)
                        }
                    }
                }
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideUsersApiService(): GeolocationApiService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl("https://www.googleapis.com")
            .build()

        return retrofit.create(GeolocationApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        // return default dispatcher instance that will be used in production
        // as we will need different dispatchers for tests
        return object : DispatcherProvider {
            // using default dispatchers as in DispatcherProvider
        }
    }
}