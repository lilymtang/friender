package com.example.twitchandroidproject.di

import android.content.Context
import androidx.room.Room
import com.example.twitchandroidproject.repository.api.GeolocationApiService
import com.example.twitchandroidproject.repository.database.FrienderDatabase
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

// DI module that replaces Main DI module
// to create test dependencies instead of actual dependencies
// https://developer.android.com/training/dependency-injection/hilt-testing
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MainModule::class]
)
object AndroidTestMainModule {

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext applicationContext: Context): FrienderDatabase {
        return Room.inMemoryDatabaseBuilder(
            applicationContext,
            FrienderDatabase::class.java,
        )
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideUsersApiService(): GeolocationApiService {
        // TODO: replace with mock service
        val retrofit = Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl("https://www.googleapis.com")
            .build()

        return retrofit.create(GeolocationApiService::class.java)
    }

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        // return test dispatcher provider that allow running coroutines on correct threads
        // during testing
        return object : DispatcherProvider {
            override fun default() = TestCoroutineDispatcher()
            override fun io() = TestCoroutineDispatcher()
            override fun main() = TestCoroutineDispatcher()
            override fun unconfined() = TestCoroutineDispatcher()
        }
    }
}