package com.example.twitchandroidproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.twitchandroidproject.repository.FrienderRepository
import com.example.twitchandroidproject.repository.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.BackpressureStrategy
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
        private val frienderRepository: FrienderRepository
) : ViewModel() {
    private val userProfiles: LiveData<List<UserProfile>> = LiveDataReactiveStreams.fromPublisher(
        frienderRepository
            .getOtherUsersNearby()
            .toFlowable(BackpressureStrategy.BUFFER)
    )

    fun getUserProfiles(): LiveData<List<UserProfile>> {
        return userProfiles
    }
}