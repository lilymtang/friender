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
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date
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
        val allUserProfiles = TestDataUtil.createInitialUserProfiles()

        // WHEN
        database.userProfileDao().insertAll(allUserProfiles)

        // THEN
        repository
            .getCurrentUserProfile()
            .test()
            .assertNoErrors()
            .assertValue { userProfile -> userProfile.userProfileType == UserProfile.UserProfileType.CURRENT_USER }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testSaveCurrentUserProfile() = runBlockingTest {
        // HAVING
        val currentUserProfile = UserProfile(
            "test@email.com",
            UserProfile.UserProfileType.CURRENT_USER,
            false, // not available
            "Test User",
            Date(),
            "test bio",
            null, // no picture
            listOf("interest1", "interest2", "interest3"),
            listOf("preferredInterest1", "preferredInterest2", "preferredInterest3")
        )

        // WHEN
        repository.saveCurrentUserProfile(currentUserProfile)

        // THEN
        repository
            .getCurrentUserProfile()
            .test()
            .assertNoErrors()
            .assertValue { userProfile ->
                userProfile.equals(currentUserProfile)
            }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testRetrieveAvailableInterests() = runBlockingTest {

        // HAVING
        val allAvailableInterests =
            context.resources.getStringArray(R.array.available_interests).toList().sorted()

        // WHEN
        val result = repository.getAvailableInterests()

        // THEN
        // Checking that available interests from resources contain the same elements as returned by Repository
        assertThat(allAvailableInterests.toTypedArray().contentEquals(result.toTypedArray()))
    }
}
