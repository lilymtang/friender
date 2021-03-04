package com.example.twitchandroidproject.repository

import com.example.twitchandroidproject.repository.TestDataUtil.createInitialUserProfiles
import com.example.twitchandroidproject.repository.api.GeolocationApiService
import com.example.twitchandroidproject.repository.database.FrienderDatabase
import com.example.twitchandroidproject.repository.model.UserProfile
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FrienderRepository @Inject constructor(
    private val database: FrienderDatabase,
    private val geolocationService: GeolocationApiService
) {

    init {
        // launching on background thread due to suspend functions
        GlobalScope.launch {
            val userProfileCount = database.userProfileDao().getCount()
            if (userProfileCount == 0) {
                val userProfiles = createInitialUserProfiles()
                database.userProfileDao().insertAll(userProfiles)
            }
        }
    }

    fun getAllUsersNearby() =
        database.userProfileDao().getAll(
            userProfileTypes = listOf(
                UserProfile.UserProfileType.FRIEND,
                UserProfile.UserProfileType.OTHER
            )
        )

    fun getFriendsNearby() =
        database.userProfileDao()
            .getAll(userProfileTypes = listOf(UserProfile.UserProfileType.FRIEND))

}
