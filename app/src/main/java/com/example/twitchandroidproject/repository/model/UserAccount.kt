package com.example.twitchandroidproject.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Class to store logged in user's information
 */

@Entity
data class UserAccount(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    var email: String,
    var password: String

)