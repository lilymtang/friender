package com.example.twitchandroidproject.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date

class BindingAdapters {
    companion object {
        @BindingAdapter("dateOfBirth")
        @JvmStatic
        fun dateOfBirthToAge(view: TextView, dateOfBirth: Date?) {
            // LiveData is null initially because of async Room call in ProfileFragmentViewModel
            if(dateOfBirth == null){
                view.text = Date().toString()
                return
            }

            val years =
                ChronoUnit.YEARS.between(dateOfBirth.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime(), LocalDateTime.now())
            view.text = years.toString()
        }
    }
}
