package com.example.twitchandroidproject.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.repository.FrienderRepository
import com.example.twitchandroidproject.repository.TestDataUtil
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@LargeTest
class LoginScreenTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var repository: FrienderRepository


    @ExperimentalCoroutinesApi
    @Before
    fun setupDatabaseWithRegisteredUserAccount() = runBlockingTest {
        hiltRule.inject()

        val currentUserProfile = TestDataUtil.createCurrentUserProfile()
        val currentUserAccount = TestDataUtil.createInitialUserAccount()

        repository.registerNewUserAccount(
            currentUserAccount.email, currentUserAccount.password,
            currentUserProfile
        )
    }

    @Test
    fun loginButtonEnabledWhenEmailAndPasswordAreEntered() {

        onView(withId(R.id.emailText))
            .perform(typeText("testEmail"))
        onView(withId(R.id.passwordText))
            .perform(typeText("testPassword"))

        // Expected

        onView(withId(R.id.signInButton))
            .check(matches(isEnabled()))
    }

    @Test
    fun loginWithIncorrectPasswordDisplaysIncorrectPasswordError() {

        onView(withId(R.id.emailText))
            .perform(typeText("user"))
        onView(withId(R.id.passwordText))
            .perform(typeText("testPassword"))
        onView(withId(R.id.signInButton))
            .perform(click())

        // Expected

        onView(withText(R.string.error_message_incorrect_password))
            .check(matches(isDisplayed()))
    }

    @Test
    fun loginWithIncorrectEmailDisplaysNotRegisteredUserError() {

        onView(withId(R.id.emailText))
            .perform(typeText("testEmail"))
        onView(withId(R.id.passwordText))
            .perform(typeText("testPassword"))
        onView(withId(R.id.signInButton))
            .perform(click())

        // Expected

        onView(withText(R.string.error_message_not_registered_user))
            .check(matches(isDisplayed()))
    }

    @Test
    fun successfulLoginOpensHomeScreen() {

        onView(withId(R.id.emailText))
            .perform(typeText("user"))
        onView(withId(R.id.passwordText))
            .perform(typeText("user"))
        onView(withId(R.id.signInButton))
            .perform(click())

        // Expected

        onView(withId(R.id.homeNavViewItemContainer))
            .check(matches(isDisplayed()))
    }
}