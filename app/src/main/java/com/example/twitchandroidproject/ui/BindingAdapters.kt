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
        fun DateOfBirthToAge(view: TextView, dateOfBirth: Date) {
            val years =
                ChronoUnit.YEARS.between(dateOfBirth.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime(), LocalDateTime.now())
            view.text = years.toString()
        }

        @BindingAdapter(value=["preferredInterest", "index"])
        @JvmStatic
        fun preferredInterestListByIndex(view: TextView, preferredInterests: List<String>, i: Int) {
            view.text = preferredInterests[i]
        }
    }
}
