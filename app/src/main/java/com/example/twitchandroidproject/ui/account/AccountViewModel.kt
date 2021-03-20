package com.example.twitchandroidproject.ui.account

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.toLiveData
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
class AccountViewModel @Inject constructor(
    application: Application,
    private val frienderRepository: FrienderRepository,
) : AndroidViewModel(application) {

    val currentUserProfile = Transformations.map(
        frienderRepository.getCurrentUserProfile().toLiveData()
    ) { userProfile ->
        // when data is retrieved we want to set initial values for each field
        setInitialFieldValues(userProfile)

        userProfile
    }

    val profilePicture = MutableLiveData<Bitmap?>(null)

    var displayName = MutableLiveData<String?>(null)
    val displayNameValidationError = notBlankValidationObserver(application, displayName)

    val dateOfBirth = MutableLiveData<Date?>(null)
    val dateOfBirthValidatorError = dateOfBirthValidationObserver(application, dateOfBirth)

    val bio = MutableLiveData<String?>(null)
    val bioValidationError = notBlankValidationObserver(application, bio)

    val isAvailableToHangout = MutableLiveData<Boolean?>(null)

    // To enable get started button we want to make sure that
    // there are no errors on each field and all field values are entered
    private val allFieldErrorsAreNull = transformationsMapAll(
        listOf(
            displayNameValidationError,
            dateOfBirthValidatorError,
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
            bio
        )
    ) { values ->
        // check that all field values are entered (not null)
        values.none { it == null }
    }

    // get started button enabled when "all errors are null" and "all fields are not null"
    val saveButtonEnabled =
        transformationsMapAll(listOf(allFieldErrorsAreNull, allFieldsValuesAreNotNull)) { values ->
            // all conditions are true
            values.all { it == true }
        }


    // inner function to initialize all attributes based on user profile received
    private fun setInitialFieldValues(userProfile: UserProfile) {
        displayName.value = userProfile.fullName
        dateOfBirth.value = userProfile.dateOfBirth
        bio.value = userProfile.bio
        profilePicture.value = userProfile.profilePicture
        isAvailableToHangout.value = userProfile.isAvailableToHangout
    }


    // property to notify view when registration was successful
    private val _saveMessage = MutableLiveData<String?>()
    val saveMessage: LiveData<String?>
        get() = _saveMessage

    // property for notify view the "event" when logout was successful
    private val _eventLogoutSuccessful = MutableLiveData<Boolean>()
    val eventLogoutSuccessful: LiveData<Boolean>
        get() = _eventLogoutSuccessful

    // used to mark event as completed to avoid handling same event 2 times
    fun markEventLogoutHandled() {
        _eventLogoutSuccessful.value = false
    }

    // used to mark event as completed to avoid handling same event 2 times
    fun markSaveMessageDisplayedHandled() {
        _saveMessage.value = null
    }

    fun save() {
        currentUserProfile.value?.let { userProfile ->
            userProfile.isAvailableToHangout = isAvailableToHangout.value!!
            userProfile.fullName = displayName.value!!
            userProfile.dateOfBirth = dateOfBirth.value!!
            userProfile.bio = bio.value!!

            viewModelScope.launch {
                try {
                    frienderRepository.updateCurrentUserProfile(userProfile)
                    _saveMessage.value = getApplication<Application>().resources.getString(
                        R.string.account_fragment_message_profile_updated
                    )
                } catch (e: Exception) {
                    _saveMessage.value = getApplication<Application>().resources.getString(
                        R.string.account_fragment_message_profile_update_error,
                        e.message
                    )
                }
            }
        }
    }

    fun logout() {
        frienderRepository.logout()
        _eventLogoutSuccessful.value = true
    }
}
