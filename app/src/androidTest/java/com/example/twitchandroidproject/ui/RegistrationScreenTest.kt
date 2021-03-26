package com.example.twitchandroidproject.ui

import android.widget.DatePicker
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.ui.utils.EspressoUtils.Companion.scrollTo
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@LargeTest
class RegistrationScreenTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun navigateToRegistrationScreen() {
        hiltRule.inject()

        onView(withId(R.id.createNewAccountButton))
            .perform(click())
    }

    @Test
    fun getStartedButtonEnabledWhenAllFieldsAreEntered() {

        onView(withId(R.id.usernameText))
            .perform(typeText("testUsername"), closeSoftKeyboard())

        onView(withId(R.id.dateOfBirthField))
            .perform(click())
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name))).perform(
            PickerActions.setDate(
                2000,
                3,
                23
            )
        )
        // press on OK button
        onView(withId(android.R.id.button1))
            .perform(click())

        onView(withId(R.id.emailText))
            .perform(typeText("testEmail"), closeSoftKeyboard())

        onView(withId(R.id.phoneText))
            .perform(typeText("1234567890"), closeSoftKeyboard())

        onView(withId(R.id.passwordText))
            .perform(typeText("testPassword"), closeSoftKeyboard())

        onView(withId(R.id.confirmPasswordText))
            .perform(scrollTo(), typeText("testPassword"), closeSoftKeyboard())

        onView(withId(R.id.bioText))
            .perform(scrollTo(), typeText("testBio"), closeSoftKeyboard())

        // Expected

        onView(withId(R.id.getStartedButton))
            .perform(scrollTo())
            .check(matches(ViewMatchers.isEnabled()))
    }

    @Test
    fun differentConfirmedPasswordDisplaysDoesNotMatchPasswordError() {

        onView(withId(R.id.passwordText))
            .perform(typeText("testPassword"), closeSoftKeyboard())

        onView(withId(R.id.confirmPasswordText))
            .perform(scrollTo(), typeText("testConfirmedPassword"))

        // Expected

        onView(ViewMatchers.withText(R.string.error_validation_password_does_not_match))
            .check(matches(isDisplayed()))
    }
}