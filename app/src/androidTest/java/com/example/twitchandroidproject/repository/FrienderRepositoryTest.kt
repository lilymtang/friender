package com.example.twitchandroidproject.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.repository.database.FrienderDatabase
import com.example.twitchandroidproject.repository.model.UserProfile
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
class FrienderRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var repository: FrienderRepository

    @ApplicationContext
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var database: FrienderDatabase

    @Before
    fun injectDependencies() {
        hiltRule.inject()
    }

    @After
    fun cleanDatabase() {
        database.close()
    }

    // Tests

    @Test
    @ExperimentalCoroutinesApi
    fun testRetrieveOtherUserProfiles() = runBlockingTest {

        // HAVING
        val allUserProfiles = TestDataUtil.createInitialUserProfiles()
        val allUserProfileCount = allUserProfiles
            .filter { it.userProfileType == UserProfile.UserProfileType.OTHER }
            .count()

        // WHEN
        database.userProfileDao().insertAll(allUserProfiles)

        // THEN
        repository
            .getOtherUsersNearby()
            .test()
            .assertNoErrors()
            .assertValue { list ->
                list.size == allUserProfileCount
            }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testRetrieveAllFriendsProfiles() = runBlockingTest {

        // HAVING
        registerUserAndLogin()

        val allUserProfiles = TestDataUtil.createInitialUserProfiles()
        val friendsProfileCount = allUserProfiles
            .filter { it.userProfileType == UserProfile.UserProfileType.FRIEND }
            .count()

        // WHEN
        database.userProfileDao().insertAll(allUserProfiles)

        // THEN
        repository
            .getFriends()
            .test()
            .assertNoErrors()
            .assertValue { list ->
                list.size == friendsProfileCount
            }
    }


    @Test
    @ExperimentalCoroutinesApi
    fun testRetrieveCurrentUserProfile() = runBlockingTest {

        // HAVING
        registerUserAndLogin()

        // WHEN
        repository
            .getCurrentUserProfile()

            // THEN
            .test()
            .assertNoErrors()
            .assertValue { userProfile -> userProfile.userProfileType == UserProfile.UserProfileType.CURRENT_USER }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testUpdateCurrentUserProfile() = runBlockingTest {

        // HAVING
        registerUserAndLogin()

        val currentUserProfile = repository.getCurrentUserProfile().blockingFirst()

        // WHEN
        val updatedBio = "updated bio"
        currentUserProfile.bio = updatedBio

        repository.updateCurrentUserProfile(currentUserProfile)

        // THEN
        repository
            .getCurrentUserProfile()
            .test()
            .assertNoErrors()
            .assertValue { userProfile ->
                userProfile.bio == updatedBio
            }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testRetrieveAvailableInterests() = runBlockingTest {

        // HAVING
        registerUserAndLogin()

        val allAvailableInterests =
            context.resources.getStringArray(R.array.available_interests).toList().sorted()

        // WHEN
        val result = repository.getAvailableInterests()

        // THEN
        // Checking that available interests from resources contain the same elements as returned by Repository
        assertThat(allAvailableInterests.toTypedArray().contentEquals(result.toTypedArray()))
    }

    private fun registerUserAndLogin() = runBlocking {
        val currentUserProfile = TestDataUtil.createCurrentUserProfile()

        repository.registerNewUserAccount(
            currentUserProfile.email, TestDataUtil.ACCOUNT_PASSWORD_CORRECT,
            currentUserProfile
        )

        repository.logIn(currentUserProfile.email, TestDataUtil.ACCOUNT_PASSWORD_CORRECT)
    }
}
