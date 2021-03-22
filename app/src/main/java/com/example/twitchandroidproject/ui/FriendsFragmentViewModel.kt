package com.example.twitchandroidproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import androidx.lifecycle.viewModelScope
import com.example.twitchandroidproject.repository.FrienderRepository
import com.example.twitchandroidproject.repository.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsFragmentViewModel @Inject constructor(
    private val frienderRepository: FrienderRepository
) : ViewModel() {
    fun removeFriend(userProfile: UserProfile) {
        viewModelScope.launch {
            frienderRepository.removeFriend(userProfile)
        }
    }

    fun addFriend(userProfile: UserProfile) {
        viewModelScope.launch {
            frienderRepository.addFriend(userProfile)
        }
    }
    val friendProfiles: LiveData<List<UserProfile>> = frienderRepository.getFriends().toLiveData()
}