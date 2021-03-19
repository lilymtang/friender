package com.example.twitchandroidproject.repository

import com.example.twitchandroidproject.repository.model.UserAccount
import com.example.twitchandroidproject.repository.model.UserProfile
import java.util.Calendar


/**
 * Object containing helper functions / constants for test data creation
 */
object TestDataUtil {

    const val ACCOUNT_PASSWORD_CORRECT = "correctPassword"
    const val ACCOUNT_PASSWORD_INCORRECT = "incorrectPassword"

    fun createInitialUserProfiles() = listOf(

        createCurrentUserProfile(),

        UserProfile(
            email = "user2@email.com",
            userProfileType = UserProfile.UserProfileType.FRIEND,
            isAvailableToHangout = false,
            fullName = "User2",
            dateOfBirth = dateOfBirthForAge(72),
            bio = "bio2",
            profilePicture = null,
            interests = listOf("interest1", "interest2", "interest3"),
            preferredInterests = listOf(
                "preferredInterest1",
                "preferredInterest2",
                "preferredInterest3"
            )
        ),
        UserProfile(
            email = "user3@email.com",
            userProfileType = UserProfile.UserProfileType.FRIEND,
            isAvailableToHangout = true,
            fullName = "User3",
            dateOfBirth = dateOfBirthForAge(27),
            bio = "bio3",
            profilePicture = null,
            interests = listOf("interest1", "interest2", "interest3"),
            preferredInterests = listOf(
                "preferredInterest1",
                "preferredInterest2",
                "preferredInterest3"
            )
        ),
        UserProfile(
            email = "user4@email.com",
            userProfileType = UserProfile.UserProfileType.FRIEND,
            isAvailableToHangout = true,
            fullName = "User4",
            dateOfBirth = dateOfBirthForAge(51),
            bio = "bio4",
            profilePicture = null,
            interests = listOf("interest1", "interest2", "interest3"),
            preferredInterests = listOf(
                "preferredInterest1",
                "preferredInterest2",
                "preferredInterest3"
            )
        ),
        UserProfile(
            email = "user5@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = false,
            fullName = "Lily",
            dateOfBirth = dateOfBirthForAge(31),
            bio = "bio5",
            profilePicture = null,
            interests = listOf("interest1", "interest2", "interest3"),
            preferredInterests = listOf(
                "backpacking",
                "baking",
                "photography"
            )
        ),
        UserProfile(
            email = "user6@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "Moni",
            dateOfBirth = dateOfBirthForAge(25),
            bio = "bio6",
            profilePicture = null,
            interests = listOf("interest1", "interest2", "interest3"),
            preferredInterests = listOf(
                "art",
                "yoga",
                "running"
            )
        ),
        UserProfile(
            email = "user7@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "Amy",
            dateOfBirth = dateOfBirthForAge(18),
            bio = "bio7",
            profilePicture = null,
            interests = listOf("interest1", "interest2", "interest3"),
            preferredInterests = listOf(
                "rock climbing",
                "singing",
                "piano"
            )
        ),
        UserProfile(
            email = "user8@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = false,
            fullName = "Lara Jean",
            dateOfBirth = dateOfBirthForAge(34),
            bio = "bio8",
            profilePicture = null,
            interests = listOf("interest1", "interest2", "interest3"),
            preferredInterests = listOf(
                "backpacking",
                "baking",
                "photography"
            )
        ),
        UserProfile(
            email = "user9@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "Jonathan",
            dateOfBirth = dateOfBirthForAge(40),
            bio = "bio9",
            profilePicture = null,
            interests = listOf("interest1", "interest2", "interest3"),
            preferredInterests = listOf(
                "art"
            )
        ),
        UserProfile(
            email = "user10@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "Christopher",
            dateOfBirth = dateOfBirthForAge(30),
            bio = "bio10",
            profilePicture = null,
            interests = listOf("interest1", "interest2", "interest3"),
            preferredInterests = listOf(
                "rock climbing",
                "singing"
            )
        )
    )

    fun createCurrentUserProfile() =
        UserProfile(
            email = "user",
            userProfileType = UserProfile.UserProfileType.CURRENT_USER,
            isAvailableToHangout = true,
            fullName = "User1",
            dateOfBirth = dateOfBirthForAge(20),
            bio = "bio1",
            profilePicture = null,
            interests = listOf("interest1", "interest2", "interest3"),
            preferredInterests = listOf(
                "preferredInterest1",
                "preferredInterest2",
                "preferredInterest3"
            )
        )

    fun createInitialUserAccount() =
        UserAccount(email = "user", password = "user")

    // helper function that creates dateOfBirth of provided age
    // to simplify test user data creation
    private fun dateOfBirthForAge(age: Int) =
        Calendar.getInstance().apply {
            add(Calendar.YEAR, -age)
        }.time
}