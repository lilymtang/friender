package com.example.twitchandroidproject.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.example.twitchandroidproject.repository.FrienderRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationManager @Inject constructor(
    @ApplicationContext private val context: Context
): LiveData<Location?>() {
    var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private var locationRequest: LocationRequest? = null

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            value = locationResult.lastLocation
        }
    }

    fun startService() {
        onActive()
    }

    @Synchronized
    private fun createLocationRequest() {
        locationRequest = LocationRequest.create()
        locationRequest?.interval = 60000 // Update every 1 minute
        locationRequest?.fastestInterval = 60000
        locationRequest?.smallestDisplacement = 170f // 170 m = 0.1 mile
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onActive() {
        super.onActive()
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        createLocationRequest()
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onInactive() {
        super.onActive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}