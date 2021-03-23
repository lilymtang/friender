package com.example.twitchandroidproject

import android.app.Application
import com.example.twitchandroidproject.repository.TestDataUtil
import com.example.twitchandroidproject.repository.database.FrienderDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class FrienderApplication : Application() {

    @Inject
    lateinit var database: FrienderDatabase

    override fun onCreate() {
        super.onCreate()

        createInitialDataIfNotExists()
    }


    private fun createInitialDataIfNotExists() {

        val userProfileDao = database.userProfileDao()
        val userAccountDao = database.userAccountDao()

        GlobalScope.launch {
            val userProfileCount = userProfileDao.getCount()
            if (userProfileCount == 0) {
                val userAccount = TestDataUtil.createInitialUserAccount()
                userAccountDao.create(userAccount)

                val userProfiles = TestDataUtil.createInitialUserProfiles()
                userProfileDao.insertAll(userProfiles)
            }
        }
    }
}