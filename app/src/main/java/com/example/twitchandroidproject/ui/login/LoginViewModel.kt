package com.example.twitchandroidproject.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitchandroidproject.repository.FrienderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val frienderRepository: FrienderRepository,
) : ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    //Email error messages
    private val _emailErrorMessage = MutableLiveData<String?>()
    val emailErrorMessage: LiveData<String?>
        get() = _emailErrorMessage

    //Password error messages
    private val _passwordErrorMessage = MutableLiveData<String?>()
    val passwordErrorMessage: LiveData<String?>
        get() = _passwordErrorMessage

    // status of signin button (disabled/enabled)
    // changed each time email or password property values are changed
    // to allow signing in only when both email and password are entered
    val signInButtonEnabled: LiveData<Boolean> = Transformations.switchMap(email) { emailValue ->
        Transformations.map(password) { passwordValue ->
            passwordValue.isNotEmpty() && emailValue.isNotEmpty()
        }
    }

    // TODO: in future we can also validate if entered email / password are valid
    // as user enters data (currently no validation is implemented)

    // property for notify view the "event" when login was successful
    private val _eventLoginSuccessful = MutableLiveData<Boolean>()
    val eventLoginSuccessful: LiveData<Boolean>
        get() = _eventLoginSuccessful

    // used to mark event as completed to avoid handling same event 2 times
    fun markEventLoginSuccessfulHandled() {
        _eventLoginSuccessful.value = false
    }


    fun login() {
        val emailValue = email.value ?: ""
        val passwordValue = password.value ?: ""

        viewModelScope.launch {
            try {
                frienderRepository.logIn(emailValue, passwordValue)
                _eventLoginSuccessful.value = true
            } catch (e: IllegalArgumentException) {
                _passwordErrorMessage.value = e.message
            }
        }
    }
}