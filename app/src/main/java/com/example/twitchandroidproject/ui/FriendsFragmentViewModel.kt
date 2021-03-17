package com.example.twitchandroidproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitchandroidproject.repository.FrienderRepository
import com.example.twitchandroidproject.repository.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.BackpressureStrategy
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsFragmentViewModel @Inject constructor(
    private val frienderRepository: FrienderRepository
) : ViewModel() {

    // automatically change data when user login status is changing
    val friendProfiles: LiveData<List<UserProfile>> =
        Transformations.switchMap(frienderRepository.isUserLoggedIn) { isLoggedIn ->

            if (isLoggedIn) {
                // return data as normal
                LiveDataReactiveStreams.fromPublisher(
                    frienderRepository
                        .getFriends()
                        .toFlowable(BackpressureStrategy.BUFFER)
                )
            } else {
                // TODO: temporary solution while security is not integrated with navigation
                // and user is able to access sections that he can not while not authenticated

                // doing login
                viewModelScope.launch {
                    frienderRepository.logIn("user1@email.com", "password1")
                }

                // and returning empty data meanwhile
                MutableLiveData(listOf())
            }
        }

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
}