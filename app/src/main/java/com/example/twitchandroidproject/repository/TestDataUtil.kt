package com.example.twitchandroidproject.repository

import com.example.twitchandroidproject.repository.model.UserProfile
import java.util.Date

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
            isAvailableToHangout = true,
            fullName = "User2",
            dateOfBirth = Date(),
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
            dateOfBirth = Date(),
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
            dateOfBirth = Date(),
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
            isAvailableToHangout = true,
            fullName = "User5",
            dateOfBirth = Date(),
            bio = "bio5",
            profilePicture = null,
            interests = listOf("interest1", "interest2", "interest3"),
            preferredInterests = listOf(
                "preferredInterest1",
                "preferredInterest2",
                "preferredInterest3"
            )
        ),
        UserProfile(
            email = "user6@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "User6",
            dateOfBirth = Date(),
            bio = "bio6",
            profilePicture = null,
            interests = listOf("interest1", "interest2", "interest3"),
            preferredInterests = listOf(
                "preferredInterest1",
                "preferredInterest2",
                "preferredInterest3"
            )
        )
    )

    fun createCurrentUserProfile() =
        UserProfile(
            email = "user1@email.com",
            userProfileType = UserProfile.UserProfileType.CURRENT_USER,
            isAvailableToHangout = true,
            fullName = "User1",
            dateOfBirth = Date(),
            bio = "bio1",
            profilePicture = null,
            interests = listOf("interest1", "interest2", "interest3"),
            preferredInterests = listOf(
                "preferredInterest1",
                "preferredInterest2",
                "preferredInterest3"
            )
        )
}