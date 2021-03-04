package com.example.twitchandroidproject.repository

import com.example.twitchandroidproject.repository.api.GeolocationApiService
import com.example.twitchandroidproject.repository.database.FrienderDatabase
import com.example.twitchandroidproject.repository.model.UserProfile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FrienderRepository @Inject constructor(
    private val database: FrienderDatabase,
    private val geolocationService: GeolocationApiService
) {

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
