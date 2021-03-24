package com.example.twitchandroidproject.ui

import android.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import androidx.lifecycle.viewModelScope
import com.example.twitchandroidproject.repository.FrienderRepository
import com.example.twitchandroidproject.repository.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
        private val frienderRepository: FrienderRepository,
        state: SavedStateHandle
) : ViewModel() {
    val userProfile: LiveData<UserProfile> = loadUserProfile(state.get("userId")!!)
    val profileIsVisible: LiveData<Boolean> = Transformations.map(userProfile) {
        it != null
    }

    private fun loadUserProfile(userId: Long): LiveData<UserProfile> {
        return frienderRepository.getUserById(userId).toLiveData()
    }

    fun addFriend() {
        viewModelScope.launch {
            frienderRepository.addFriend(userProfile.value!!)
        }
    }
}