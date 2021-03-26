package com.example.twitchandroidproject.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.di.DispatcherProvider
import com.example.twitchandroidproject.repository.api.GeolocationApiService
import com.example.twitchandroidproject.repository.database.FrienderDatabase
import com.example.twitchandroidproject.repository.model.UserAccount
import com.example.twitchandroidproject.repository.model.UserProfile
import com.example.twitchandroidproject.ui.LocationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FrienderRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: FrienderDatabase,
    private val dispatcherProvider: DispatcherProvider
) {
    private var currentlyLoggedInUserEmail = MutableLiveData<String?>(null)

    /**
     * Allows observing current user log in status
     */
    // TODO: switch to RxJava API in future
    val isUserLoggedIn: LiveData<Boolean> =
        Transformations.map(currentlyLoggedInUserEmail) { loggedInUserEmail ->
            loggedInUserEmail != null
        }

    /**
     * Gets other users nearby
     * @param id user's id
     * @return Flowable UserProfile
     */
    fun getUserById(id: Long) = database.userProfileDao().getById(id)

    /**
     * Gets users with bios or interests that match the search query
     * @param keyword search query
     * @return Flowable<List<UserProfile>>
     */
    fun getUsersByKeyword(keyword: String): Flowable<List<UserProfile>> {
        return database.userProfileDao().getUsersByKeyword(keyword, listOf(UserProfile.UserProfileType.OTHER))
    }

    /**
     * Gets other users nearby
     *
     * @return Flowable list of UserProfiles
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
     * @return Flowable list of UserProfiles
     *
     * @throws IllegalStateException in case if user is not logged in
     */
    fun getFriends(): Flowable<List<UserProfile>> {
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

            // Automatically log in user at the end of the registration flow
            logIn(email, password)
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

            withContext(dispatcherProvider.main()) {
                currentlyLoggedInUserEmail.value = email
            }
        }
    }

    fun logout() {
        currentlyLoggedInUserEmail.value = null
    }

    /**
     * Updates current user profile with latitude and longitude
     * @param latitude latitude from LocationManager
     * @param longitude latitude from LocationManager
     */
    suspend fun updateCurrentUserLocation(latitude: Double, longitude: Double) {
        throwErrorIfNotLoggedIn()
        database.userProfileDao().updateCurrentUserLocation(latitude, longitude)
    }

    /**
     * Updates current user profile to the database
     *
     * @param userProfile user profile to update (profile type must be CURRENT_USER)
     *
     * @throws IllegalStateException in case if user is not logged in
     */
    suspend fun addFriend(userProfile: UserProfile) {
        throwErrorIfNotLoggedIn()

        withContext(dispatcherProvider.io()) {
            if (userProfile.userProfileType == UserProfile.UserProfileType.OTHER) {
                // Change the user to a friend
                userProfile.userProfileType = UserProfile.UserProfileType.FRIEND
                database.userProfileDao().updateUserProfile(userProfile)
            }
        }
    }

    /**
     * Updates current user profile to the database
     *
     * @param userProfile user profile to update (profile type must be CURRENT_USER)
     *
     * @throws IllegalStateException in case if user is not logged in
     */
    suspend fun removeFriend(userProfile: UserProfile) {
        // TODO: uncomment when login flow is complete
        // throwErrorIfNotLoggedIn()

        withContext(dispatcherProvider.io()) {
            if (userProfile.userProfileType == UserProfile.UserProfileType.FRIEND) {
                // Change the user to not a friend
                userProfile.userProfileType = UserProfile.UserProfileType.OTHER
                database.userProfileDao().updateUserProfile(userProfile)
            }
        }
    }

    /**
     * Gets profile of the current user
     *
     * @return Observable UserProfile
     *
     * @throws IllegalStateException in case if user is not logged in
     */
    fun getCurrentUserProfile(): Flowable<UserProfile> {
        throwErrorIfNotLoggedIn()

        return database.userProfileDao().getByProfileTypeAndEmail(
            UserProfile.UserProfileType.CURRENT_USER,
            currentlyLoggedInUserEmail.value!!
        )
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
        if (currentlyLoggedInUserEmail.value == null) {
            throw IllegalStateException(context.getString(R.string.error_message_not_logged_in_user))
        }
    }
}
