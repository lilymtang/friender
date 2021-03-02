package com.example.twitchandroidproject.repository.api

import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET


interface GeolocationApiService {

    // todo: Replace with actual API once identified
    // https://developers.google.com/maps/documentation/geolocation/overview
    @GET("/geolocation/v1/geolocate")
    fun getLocation(): Flowable<Void>
}