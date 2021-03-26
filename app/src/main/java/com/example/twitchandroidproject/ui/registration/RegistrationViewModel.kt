package com.example.twitchandroidproject.ui.registration

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.repository.FrienderRepository
import com.example.twitchandroidproject.repository.model.UserProfile
import com.example.twitchandroidproject.ui.utils.dateOfBirthValidationObserver
import com.example.twitchandroidproject.ui.utils.notBlankValidationObserver
import com.example.twitchandroidproject.ui.utils.transformationsMapAll
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    application: Application,
    private val frienderRepository: FrienderRepository
) : AndroidViewModel(application) {

    val profilePicture = MutableLiveData<Bitmap>(null)

    val displayName = MutableLiveData<String?>(null)
    val displayNameValidationError = notBlankValidationObserver(application, displayName)

    val dateOfBirth = MutableLiveData<Date?>(null)
    val dateOfBirthValidatorError = dateOfBirthValidationObserver(application, dateOfBirth)

    val phone = MutableLiveData<String?>(null)
    val phoneValidationError = notBlankValidationObserver(application, phone)

    val email = MutableLiveData<String?>(null)
    val emailValidationError = notBlankValidationObserver(application, email)

    val password = MutableLiveData<String?>(null)
    val passwordValidationError = notBlankValidationObserver(application, password)

    val confirmedPassword = MutableLiveData<String?>(null)
    val confirmedPasswordValidationError: LiveData<String?> =
        transformationsMapAll(listOf(password, confirmedPassword)) { values ->
            // Observing both - password and confirmed password because error message might change
            // if any of these fields changes
            val passwordValue = values[0] as String?
            val repeatedPasswordValue = values[1] as String?

            when {
                // don't show error if value is not yet entered
                repeatedPasswordValue == null -> null
                repeatedPasswordValue.isBlank() -> application.getString(R.string.error_validation_blank)
                repeatedPasswordValue != passwordValue -> application.getString(R.string.error_validation_password_does_not_match)
                else -> null
            }
        }

    val bio = MutableLiveData<String?>(null)
    val bioValidationError = notBlankValidationObserver(application, bio)

    // To enable get started button we want to make sure that
    // there are no errors on each field and all field values are entered
    private val allFieldErrorsAreNull = transformationsMapAll(
        listOf(
            displayNameValidationError,
            dateOfBirthValidatorError,
            phoneValidationError,
            emailValidationError,
            passwordValidationError,
            confirmedPasswordValidationError,
            bioValidationError
        )
    ) { values ->
        // check that all errors are null
        values.all { it == null }
    }

    private val allFieldsValuesAreNotNull = transformationsMapAll(
        listOf(
            displayName,
            dateOfBirth,
            phone,
            email,
            password,
            confirmedPassword,
            bio
        )
    ) { values ->
        // check that all field values are entered (not null)
        values.none { it == null }
    }

    // get started button enabled when "all errors are null" and "all fields are not null"
    val getStartedButtonEnabled =
        transformationsMapAll(listOf(allFieldErrorsAreNull, allFieldsValuesAreNotNull)) { values ->
            // all conditions are true
            values.all { it == true }
        }

    // Overall registration error
    private val _registrationFormError = MutableLiveData<String>()
    val registrationFormError: LiveData<String>
        get() = _registrationFormError

    // property for notify view the "event" when registration was successful
    private val _eventRegistrationSuccessful = MutableLiveData<Boolean>()
    val eventRegistrationSuccessful: LiveData<Boolean>
        get() = _eventRegistrationSuccessful

    // used to mark event as completed to avoid handling same event 2 times
    fun markEventRegistrationSuccessfulHandled() {
        _eventRegistrationSuccessful.value = false
    }

    fun register() {
        viewModelScope.launch {
            try {
                frienderRepository.registerNewUserAccount(
                    email.value!!,
                    password.value!!,
                    UserProfile(
                        email = email.value!!,
                        userProfileType = UserProfile.UserProfileType.CURRENT_USER,
                        isAvailableToHangout = false,
                        fullName = displayName.value!!,
                        dateOfBirth = dateOfBirth.value!!,
                        phoneNumber = phone.value!!,
                        bio = bio.value!!,
                        profilePicture = profilePicture.value,

                        // we are not capturing interests / preferred interests
                        // during registration process
                        interests = listOf(),
                        preferredInterests = listOf(),
                        latitude = 35.69756, // TODO: don't hardcode this
                        longitude = -120.41964
                    )
                )

                _eventRegistrationSuccessful.value = true
            } catch (e: IllegalArgumentException) {
                _registrationFormError.value = e.message
            }
        }
    }
}