package com.example.twitchandroidproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.example.twitchandroidproject.repository.FrienderRepository
import com.example.twitchandroidproject.repository.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendsFragmentViewModel @Inject constructor(
    private val frienderRepository: FrienderRepository
) : ViewModel() {

    // automatically change data when user login status is changing
    val friendProfiles: LiveData<List<UserProfile>> = frienderRepository.getFriends().toLiveData()
}