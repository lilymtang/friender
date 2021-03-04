package com.example.twitchandroidproject.repository

import com.example.twitchandroidproject.repository.model.UserProfile
import java.util.Date

object TestDataUtil {

    fun createInitialUserProfiles() = listOf(

        UserProfile(
            "user1@email.com",
            UserProfile.UserProfileType.CURRENT_USER,
            true,
            "User1",
            Date(),
            "bio1",
            null,
            listOf("interest1", "interest2", "interest3"),
            listOf("preferredInterest1", "preferredInterest2", "preferredInterest3")
        ),
        UserProfile(
            "user2@email.com",
            UserProfile.UserProfileType.FRIEND,
            true,
            "User2",
            Date(),
            "bio2",
            null,
            listOf("interest1", "interest2", "interest3"),
            listOf("preferredInterest1", "preferredInterest2", "preferredInterest3")
        ),
        UserProfile(
            "user3@email.com",
            UserProfile.UserProfileType.FRIEND,
            true,
            "User3",
            Date(),
            "bio3",
            null,
            listOf("interest1", "interest2", "interest3"),
            listOf("preferredInterest1", "preferredInterest2", "preferredInterest3")
        ),
        UserProfile(
            "user4@email.com",
            UserProfile.UserProfileType.FRIEND,
            true,
            "User4",
            Date(),
            "bio4",
            null,
            listOf("interest1", "interest2", "interest3"),
            listOf("preferredInterest1", "preferredInterest2", "preferredInterest3")
        ),
        UserProfile(
            "user5@email.com",
            UserProfile.UserProfileType.OTHER,
            true,
            "User5",
            Date(),
            "bio5",
            null,
            listOf("interest1", "interest2", "interest3"),
            listOf("preferredInterest1", "preferredInterest2", "preferredInterest3")
        ),
        UserProfile(
            "user6@email.com",
            UserProfile.UserProfileType.OTHER,
            true,
            "User6",
            Date(),
            "bio6",
            null,
            listOf("interest1", "interest2", "interest3"),
            listOf("preferredInterest1", "preferredInterest2", "preferredInterest3")
        )
    )
}