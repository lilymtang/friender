package com.example.twitchandroidproject.repository.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class UserProfile(

    @PrimaryKey
    val email: String,

    // userProfileType allows differentiating between different types of user profiles stored in the DB
    val userProfileType: UserProfileType,
    val isAvailableToHangout: Boolean,

    val fullName: String,
    val dateOfBirth: Date,
    val bio: String,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val profilePicture: Bitmap? = null,

    val interests: List<String>,
    val preferredInterests: List<String>

) {

    enum class UserProfileType {
        CURRENT_USER, FRIEND, OTHER
    }
}