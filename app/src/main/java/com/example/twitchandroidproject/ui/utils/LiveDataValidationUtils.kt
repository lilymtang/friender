package com.example.twitchandroidproject.ui.utils

import android.content.Context
import androidx.annotation.MainThread
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.example.twitchandroidproject.R
import java.util.Calendar
import java.util.Date

/**
 * Helper function similar to Transformations.map that accepts list of LiveData objects instead of single item
 * and calls map function by passing complete list of values instead of single value
 */
@MainThread
fun <T> transformationsMapAll(
    liveDataGroup: List<LiveData<out Any?>>,
    mapFunction: Function<List<Any?>, T>
): LiveData<T> {
    val result = MediatorLiveData<T>()

    // for each passed live data
    for (liveData in liveDataGroup) {

        // when it changes
        result.addSource(liveData) {

            // collect all values in the group
            val values = mutableListOf<Any?>()
            for (item in liveDataGroup) {
                values.add(item.value)
            }

            result.value = mapFunction.apply(values)
        }
    }

    return result
}

/**
 * Creates transformation that will return error in case if value becomes blank
 */
@MainThread
fun notBlankValidationObserver(context: Context, item: LiveData<String?>): LiveData<String?> =
    Transformations.map(item) { value ->
        if (value != null && value.isBlank()) {
            context.getString(R.string.error_validation_blank)
        } else {
            null
        }
    }

/**
 * Creates transformation that will return error in case if value becomes null
 */
@MainThread
fun dateOfBirthValidationObserver(context: Context, item: LiveData<Date?>): LiveData<String?> =
    Transformations.map(item) { date ->
        if (date != null) {

            // calculate minimum age that is allowed for the app (assume 16)
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -16)
            val minimumAllowedAge = calendar.time

            if (date > minimumAllowedAge) {
                return@map context.getString(R.string.error_validation_date_of_birth_too_young)
            }
        }

        // No error message
        null
    }