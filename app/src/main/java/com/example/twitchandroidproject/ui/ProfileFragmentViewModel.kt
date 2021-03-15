package com.example.twitchandroidproject.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.example.twitchandroidproject.repository.FrienderRepository
import com.example.twitchandroidproject.repository.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
        private val frienderRepository: FrienderRepository,
        state: SavedStateHandle
) : ViewModel() {
    private val userProfile: LiveData<UserProfile>

    init {
        userProfile = loadUserProfile(state.get("userId")!!)
    }

    fun getUserProfile(): LiveData<UserProfile> {
        return userProfile
    }

    private fun loadUserProfile(userId: Long): LiveData<UserProfile> {
        return frienderRepository.getUserById(userId).toLiveData()
    }
}