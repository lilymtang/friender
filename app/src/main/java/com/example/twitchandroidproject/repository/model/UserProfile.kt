package com.example.twitchandroidproject.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserProfile(

    @PrimaryKey
    val username: String,

    val email: String,
    val isAvailable: Boolean,

    // profile information (location, hometown, etc)
)