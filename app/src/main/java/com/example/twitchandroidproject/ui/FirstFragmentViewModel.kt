package com.example.twitchandroidproject.ui

import androidx.lifecycle.ViewModel
import com.example.twitchandroidproject.repository.FrienderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirstFragmentViewModel @Inject constructor(
    private val frienderRepository: FrienderRepository
) : ViewModel() {

}
