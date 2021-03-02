package com.example.twitchandroidproject.repository

import com.example.twitchandroidproject.repository.api.GeolocationApiService
import com.example.twitchandroidproject.repository.database.FrienderDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FrienderRepository @Inject constructor(
    private val database: FrienderDatabase,
    private val geolocationService: GeolocationApiService
) {}
