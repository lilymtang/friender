package com.example.twitchandroidproject.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.twitchandroidproject.repository.database.FrienderDatabase
import com.example.twitchandroidproject.repository.model.UserProfile
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    fun testRetrieveAllUserProfiles() = runBlockingTest {

        // HAVING
        val allUserProfiles = TestDataUtil.createInitialUserProfiles()
        val allUserProfileCount = allUserProfiles
            .filter { it.userProfileType != UserProfile.UserProfileType.CURRENT_USER }
            .count()

        // WHEN
        database.userProfileDao().insertAll(allUserProfiles)

        // THEN
        repository
            .getAllUsersNearby()
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
            .getFriendsNearby()
            .test()
            .assertNoErrors()
            .assertValue { list ->
                list.size == friendsProfileCount
            }
    }

}
