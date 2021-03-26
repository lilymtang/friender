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
            ),
            latitude = 35.93767,
            longitude = -119.915,
            phoneNumber = "440-789-2304"
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
            ),
            latitude = 36.01159,
            longitude = -119.99244,
            phoneNumber = "802-365-9358"
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
            ),
            latitude = 37.473,
            longitude = -121.1904,
            phoneNumber = "273-593-3467"
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
            ),
            latitude = 37.87488,
            longitude = -122.41964,
            phoneNumber = "949-930-2835"
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
            ),
            latitude = 37.77488,
            longitude = -122.41964,
            phoneNumber = "504-867-8220"
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
            ),
            latitude = 34.77488,
            longitude = -120.41964,
            phoneNumber = "812-236-6573"
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
            ),
            latitude = 35.77488,
            longitude = -119.41964,
            phoneNumber = "360-360-5243"
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
            ),
            latitude = 37.69756,
            longitude = -120.41964,
            phoneNumber = "407-300-1660"
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
            ),
            latitude = 35.69756,
            longitude = -120.41964,
            phoneNumber = "996-347-5641"
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
            ),
            latitude = 37.80488,
            longitude = -122.41964,
            phoneNumber = "461-268-7430"
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