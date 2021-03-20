package com.example.twitchandroidproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twitchandroidproject.repository.FrienderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeContainerViewModel @Inject constructor(
    private val frienderRepository: FrienderRepository,
) : ViewModel() {

    // property for notify view the "event" when logout was successful
    private val _eventLogoutSuccessful = MutableLiveData<Boolean>()
    val eventLogoutSuccessful: LiveData<Boolean>
        get() = _eventLogoutSuccessful

    // used to mark event as completed to avoid handling same event 2 times
    fun markEventLogoutHandled() {
        _eventLogoutSuccessful.value = false
    }

    fun logout() {
        frienderRepository.logout()
        _eventLogoutSuccessful.value = true
    }
}