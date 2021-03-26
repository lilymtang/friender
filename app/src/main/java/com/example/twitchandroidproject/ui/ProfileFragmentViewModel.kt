package com.example.twitchandroidproject.ui

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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
import kotlin.math.roundToInt

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
        private val frienderRepository: FrienderRepository,
        private val state: SavedStateHandle
) : ViewModel() {
    val userProfile: LiveData<UserProfile> = loadUserProfile(state.get("userId")!!)
    val currentUserProfile: LiveData<UserProfile> = frienderRepository.getCurrentUserProfile().toLiveData()

    val showSmsButton = Transformations.map(userProfile) {userProfile ->
        userProfile.userProfileType == UserProfile.UserProfileType.FRIEND
    }

    val showAddFriendButton = Transformations.map(userProfile) {userProfile ->
        userProfile.userProfileType == UserProfile.UserProfileType.OTHER
    }

    val distance = Transformations.map(userProfile.combine(currentUserProfile)) { pairLiveData ->
        getDistanceBetween(pairLiveData.first, pairLiveData.second).roundToInt().toString()
    }

    val profileIsVisible: LiveData<Boolean> = Transformations.map(userProfile) {
        it != null
    }

    fun loadUserProfile(userId: Long): LiveData<UserProfile> {
        return frienderRepository.getUserById(userId).toLiveData()
    }

    fun addFriend() {
        viewModelScope.launch {
            frienderRepository.addFriend(userProfile.value!!)
        }
    }

    fun getDistanceBetween(userProfileA: UserProfile?,
                                   userProfileB: UserProfile?): Double {
        val results = FloatArray(1)

        if ( userProfileA != null && userProfileB != null ) {
            Location.distanceBetween(
                userProfileA.latitude,
                userProfileA.longitude,
                userProfileB.latitude,
                userProfileB.longitude,
                results)
        }
        return results[0] / 1609.3440057765
    }

    class PairLiveData<A, B>(first: LiveData<A>, second: LiveData<B>) : MediatorLiveData<Pair<A?, B?>>() {
        init {
            addSource(first) { value = it to second.value }
            addSource(second) { value = first.value to it }
        }
    }

    fun <A, B> LiveData<A>.combine(other: LiveData<B>): PairLiveData<A, B> {
        return PairLiveData(this, other)
    }
}