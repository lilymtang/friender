package com.example.twitchandroidproject.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.example.twitchandroidproject.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewModel: MainActivityViewModel by viewModels()

    val LOCATION_REQUEST_CODE = 10001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDeviceLocation()

        viewModel.locationManagerWithLoginStatus.observe(this, Observer { locationManagerWithLoginStatus ->
            when(locationManagerWithLoginStatus.first) {
                true ->
                    viewModel.updateUserLocation(
                        locationManagerWithLoginStatus.second?.latitude,
                        locationManagerWithLoginStatus.second?.longitude
                )
            }
        })
    }

    private fun getDeviceLocation() {
        if (isPermissionGranted()) {
            viewModel.enableLocationServices()
        } else requestPermission()
    }

    private fun isPermissionGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        if(!isPermissionGranted()) {
            ActivityCompat.requestPermissions(this,arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getDeviceLocation()
            }
        }
    }

}