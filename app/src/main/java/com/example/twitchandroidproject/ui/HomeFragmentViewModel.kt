package com.example.twitchandroidproject.ui

import androidx.lifecycle.MutableLiveData
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
class HomeFragmentViewModel @Inject constructor(
    private val frienderRepository: FrienderRepository
) : ViewModel() {
    private val searchQuery = MutableLiveData<String?>()

    fun setSearchQuery(query: String?) {
        searchQuery.value = query
    }

    val userProfiles = Transformations.switchMap(searchQuery) {
        if(it.isNullOrEmpty()) {
            frienderRepository.getOtherUsersNearby().toLiveData()
        } else {
            frienderRepository.getUsersByKeyword(searchQuery.value!!).toLiveData()
        }
    }
}