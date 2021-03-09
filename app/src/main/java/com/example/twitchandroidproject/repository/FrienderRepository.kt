package com.example.twitchandroidproject.repository

import android.content.Context
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.di.DispatcherProvider
import com.example.twitchandroidproject.repository.api.GeolocationApiService
import com.example.twitchandroidproject.repository.database.FrienderDatabase
import com.example.twitchandroidproject.repository.model.UserAccount
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

    private var currentlyLoggedInUserEmail: String? = null

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
     *
     * @throws IllegalStateException in case if user is not logged in
     */
    fun getFriends(): Observable<List<UserProfile>> {
        throwErrorIfNotLoggedIn()

        return database.userProfileDao()
            .getAll(userProfileTypes = listOf(UserProfile.UserProfileType.FRIEND))
    }

    /**
     * Saves new user to the DB
     *
     * @param userProfile user profile to save
     * @param email user's email (to be used for logging in)
     * @param password user password (to be used for logging in)
     *
     * @throws IllegalArgumentException in case if user account with provided email already exists
     */
    suspend fun registerNewUserAccount(email: String, password: String, userProfile: UserProfile) =
        withContext(dispatcherProvider.io()) {
            if (database.userAccountDao().getCountByEmail(email) == 0) {
                database.userAccountDao().create(UserAccount(email = email, password = password))
                database.userProfileDao().createUserProfile(userProfile)
            } else {
                throw IllegalArgumentException(context.getString(R.string.error_message_duplicate_email))
            }
        }

    /**
     * Authenticates user by email and password
     *
     * @param email user's email (to be used for logging in)
     * @param password user password (to be used for logging in)
     *
     * @throws IllegalArgumentException in case if user with provided email is not registered or
     * if password is incorrect
     */
    suspend fun logIn(email: String, password: String) {
        withContext(dispatcherProvider.io()) {
            val userAccount = database.userAccountDao().getByEmail(email)

            if (userAccount == null) {
                throw IllegalArgumentException(context.getString(R.string.error_message_not_registered_user))
            }

            if (userAccount.password != password) {
                throw IllegalArgumentException(context.getString(R.string.error_message_incorrect_password))
            }

            currentlyLoggedInUserEmail = email
        }
    }

    /**
     * Gets profile of the current user
     *
     * @return Observable UserProfile
     *
     * @throws IllegalStateException in case if user is not logged in
     */
    fun getCurrentUserProfile(): Observable<UserProfile> {
        throwErrorIfNotLoggedIn()

        return (database.userProfileDao()
            .getAll(userProfileTypes = listOf(UserProfile.UserProfileType.CURRENT_USER)))
            .flatMapIterable { it }
    }


    /**
     * Updates current user profile to the database
     *
     * @param userProfile user profile to update (profile type must be CURRENT_USER)
     *
     * @throws IllegalStateException in case if user is not logged in
     * @throws IllegalArgumentException in case if incorrect profile type is used
     */
    suspend fun updateCurrentUserProfile(userProfile: UserProfile) {
        throwErrorIfNotLoggedIn()

        withContext(dispatcherProvider.io()) {
            if (userProfile.userProfileType == UserProfile.UserProfileType.CURRENT_USER) {
                database.userProfileDao().updateUserProfile(userProfile)
            } else {
                throw IllegalArgumentException("User profile type is not Current User")
            }
        }
    }

    /**
     * Provides the list of all the interests available for selection
     *
     * @return List of interest names
     */
    fun getAvailableInterests(): List<String> =
        context.resources.getStringArray(R.array.available_interests).toList().sorted()

    /**
     * Checks if user is logged in and throw an error if not
     *
     * @throws IllegalStateException in case if user is not logged in
     */
    private fun throwErrorIfNotLoggedIn() {
        if (currentlyLoggedInUserEmail == null) {
            throw IllegalStateException(context.getString(R.string.error_message_not_logged_in_user))
        }
    }
}
