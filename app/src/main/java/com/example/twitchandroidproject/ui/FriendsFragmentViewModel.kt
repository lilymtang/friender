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
class FriendsFragmentViewModel @Inject constructor(
        private val frienderRepository: FrienderRepository
) : ViewModel() {
    val friendProfiles: LiveData<List<UserProfile>> = LiveDataReactiveStreams.fromPublisher(
        frienderRepository
            .getFriends()
            .toFlowable(BackpressureStrategy.BUFFER)
    )
}