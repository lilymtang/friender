package com.example.twitchandroidproject.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
@SmallTest
class SecurityFrienderRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var repository: FrienderRepository

    @Before
    fun injectDependencies() {
        hiltRule.inject()
    }

    // Tests

    @Test
    @ExperimentalCoroutinesApi
    fun testAbleToRegisterNewUser() = runBlockingTest {

        // HAVING

        val currentUserProfile = TestDataUtil.createCurrentUserProfile()

        // WHEN

        repository.registerNewUserAccount(
            currentUserProfile.email, TestDataUtil.ACCOUNT_PASSWORD_CORRECT,
            currentUserProfile
        )

        // THEN

        // no exception
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testAbleToLoginAfterRegisterNewUser() = runBlockingTest {

        // HAVING

        val currentUserProfile = TestDataUtil.createCurrentUserProfile()

        // WHEN

        repository.registerNewUserAccount(
            currentUserProfile.email, TestDataUtil.ACCOUNT_PASSWORD_CORRECT,
            currentUserProfile
        )

        // THEN

        repository.logIn(currentUserProfile.email, TestDataUtil.ACCOUNT_PASSWORD_CORRECT)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testUnableToLoginWithWrongPassword() = runBlockingTest {

        // HAVING

        val currentUserProfile = TestDataUtil.createCurrentUserProfile()

        repository.registerNewUserAccount(
            currentUserProfile.email, TestDataUtil.ACCOUNT_PASSWORD_CORRECT,
            currentUserProfile
        )


        // WHEN

        // login using incorrect password

        // THEN

        assertThrows(IllegalArgumentException::class.java) {
            runBlockingTest {
                repository.logIn(currentUserProfile.email, TestDataUtil.ACCOUNT_PASSWORD_INCORRECT)
            }
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testAbleToCallSecuredMethodWhileLoggedIn() = runBlockingTest {

        // HAVING

        val currentUserProfile = TestDataUtil.createCurrentUserProfile()

        repository.registerNewUserAccount(
            currentUserProfile.email, TestDataUtil.ACCOUNT_PASSWORD_CORRECT,
            currentUserProfile
        )

        // WHEN

        repository.logIn(currentUserProfile.email, TestDataUtil.ACCOUNT_PASSWORD_CORRECT)

        // THEN

        repository
            .getCurrentUserProfile()
            .test()
            .assertNoErrors()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testUnableToCallSecuredMethodWhileNotLoggedIn() = runBlockingTest {

        // HAVING

        val currentUserProfile = TestDataUtil.createCurrentUserProfile()

        repository.registerNewUserAccount(
            currentUserProfile.email, TestDataUtil.ACCOUNT_PASSWORD_CORRECT,
            currentUserProfile
        )

        repository.logout()

        // WHEN

        // not logged in

        // THEN

        assertThrows(IllegalStateException::class.java) {
            repository.getCurrentUserProfile()
        }
    }
}
