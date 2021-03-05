package com.example.twitchandroidproject.repository

import android.content.Context
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.di.DispatcherProvider
import com.example.twitchandroidproject.repository.api.GeolocationApiService
import com.example.twitchandroidproject.repository.database.FrienderDatabase
import com.example.twitchandroidproject.repository.model.UserProfile
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FrienderRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: FrienderDatabase,
    private val geolocationService: GeolocationApiService,
    private val dispatcherProvider: DispatcherProvider
) {

    /**
     * Gets other users nearby
     *
     * @return Observable list of UserProfiles
     */
    fun getOtherUsersNearby() =
        database.userProfileDao().getAll(
            userProfileTypes = listOf(
                UserProfile.UserProfileType.OTHER
            )
        )

    /**
     * Gets friends nearby
     *
     * @return Observable list of UserProfiles
     */
    fun getFriends() =
        database.userProfileDao()
            .getAll(userProfileTypes = listOf(UserProfile.UserProfileType.FRIEND))


    /**
     * Gets profile of the current user
     *
     * @return Observable UserProfile
     */
    fun getCurrentUserProfile(): Observable<UserProfile> =
        (database.userProfileDao()
            .getAll(userProfileTypes = listOf(UserProfile.UserProfileType.CURRENT_USER)))
            .flatMapIterable { it }

    /**
     * Saves current user profile to the database
     *
     * @param userProfile user profile to save (profile type must be CURRENT_USER)
     */
    suspend fun saveCurrentUserProfile(userProfile: UserProfile) =
        withContext(dispatcherProvider.io()) {
            if (userProfile.userProfileType == UserProfile.UserProfileType.CURRENT_USER) {
                database.userProfileDao().createOrUpdate(userProfile)
            } else {
                throw IllegalArgumentException("User profile type is not Current User")
            }
        }

    /**
     * Provides the list of all the interests available for selection
     *
     * @return List of interest names
     */
    fun getAvailableInterests(): List<String> =
        context.resources.getStringArray(R.array.available_interests).toList().sorted()
}
