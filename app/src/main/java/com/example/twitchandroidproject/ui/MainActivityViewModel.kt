package com.example.twitchandroidproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitchandroidproject.repository.FrienderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val frienderRepository: FrienderRepository,
    private val locationManager: LocationManager
) : ViewModel() {
    val locationManagerWithLoginStatus = frienderRepository.isUserLoggedIn.combine(locationManager)

    fun enableLocationServices() {
        locationManager.startService()
    }

    fun updateUserLocation(latitude: Double?, longitude: Double?) {
        if(latitude != null && longitude != null) {
            viewModelScope.launch {
                frienderRepository.updateCurrentUserLocation(latitude, longitude)
            }
        }
    }

    private fun <A, B> LiveData<A>.combine(other: LiveData<B>): ProfileFragmentViewModel.PairLiveData<A, B> {
        return ProfileFragmentViewModel.PairLiveData(this, other)
    }
}
