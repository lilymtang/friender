package com.example.twitchandroidproject.repository.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class UserProfile(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    var email: String,

    // userProfileType allows differentiating between different types of user profiles stored in the DB
    var userProfileType: UserProfileType,
    var isAvailableToHangout: Boolean,

    var fullName: String,
    var dateOfBirth: Date,
    var bio: String,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var profilePicture: Bitmap? = null,

    var interests: List<String>,
    var preferredInterests: List<String>,

    var latitude: Double,
    var longitude: Double,

    var phoneNumber: String
) {

    enum class UserProfileType {
        CURRENT_USER, FRIEND, OTHER
    }
}