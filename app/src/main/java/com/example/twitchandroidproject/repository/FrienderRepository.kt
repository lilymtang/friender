package com.example.twitchandroidproject.repository

import com.example.twitchandroidproject.di.DispatcherProvider
import com.example.twitchandroidproject.repository.api.GeolocationApiService
import com.example.twitchandroidproject.repository.database.FrienderDatabase
import com.example.twitchandroidproject.repository.model.UserProfile
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FrienderRepository @Inject constructor(
    private val database: FrienderDatabase,
    private val geolocationService: GeolocationApiService,
    private val dispatcherProvider: DispatcherProvider
) {

    fun getOtherUsersNearby() =
        database.userProfileDao().getAll(
            userProfileTypes = listOf(
                UserProfile.UserProfileType.OTHER
            )
        )

    fun getFriends() =
        database.userProfileDao()
            .getAll(userProfileTypes = listOf(UserProfile.UserProfileType.FRIEND))

    fun getCurrentUserProfile(): Observable<UserProfile> =
        (database.userProfileDao()
            .getAll(userProfileTypes = listOf(UserProfile.UserProfileType.CURRENT_USER)))
            .flatMapIterable { it }

    suspend fun saveCurrentUserProfile(userProfile: UserProfile) =
        withContext(dispatcherProvider.io()) {
            if (userProfile.userProfileType == UserProfile.UserProfileType.CURRENT_USER) {
                database.userProfileDao().createOrUpdate(userProfile)
            } else {
                throw IllegalArgumentException("User profile type is not Current User")
            }
        }
}
