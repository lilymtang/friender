package com.example.twitchandroidproject.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.twitchandroidproject.repository.database.FrienderDatabase
import com.example.twitchandroidproject.repository.database.UserProfileDao
import com.example.twitchandroidproject.repository.model.UserProfile
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
class UserProfileDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: FrienderDatabase
    private lateinit var userProfileDao: UserProfileDao

    @Before
    fun createDatabase() {
        hiltRule.inject()
        userProfileDao = database.userProfileDao()
    }

    @After
    fun cleanDatabase() {
        database.close()
    }

    // Tests

    @ExperimentalCoroutinesApi
    @Test
    fun testCreateUserProfile() = runBlockingTest {
        // GIVEN
        val userProfile = UserProfile(
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
        userProfileDao.createOrUpdate(userProfile)

        // THEN
        userProfileDao
            .getAll(listOf(UserProfile.UserProfileType.CURRENT_USER))
            .test()
            .assertValue { list ->
                list.size == 1
            }
    }

}