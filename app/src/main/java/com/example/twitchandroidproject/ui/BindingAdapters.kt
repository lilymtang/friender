package com.example.twitchandroidproject.ui

import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale

class BindingAdapters {
    companion object {
        @BindingAdapter("dateOfBirth")
        @JvmStatic
        fun dateOfBirthToAge(view: TextView, dateOfBirth: Date) {
            val years =
                ChronoUnit.YEARS.between(
                    dateOfBirth.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime(), LocalDateTime.now()
                )
            view.text = years.toString()
        }

        // Enables setting error message on TextInputLayout as it is not available as xml property
        @BindingAdapter("errorMessage")
        @JvmStatic
        fun setErrorMessage(textInputLayout: TextInputLayout, errorMessage: String?) {
            textInputLayout.error = errorMessage
        }


        @BindingAdapter("profileImage")
        @JvmStatic
        fun setProfileImage(imageView: ImageView, profileImage: Bitmap?) {
            profileImage?.let {
                imageView.setImageBitmap(profileImage)
            }
        }
    }
}

// creating binding adapter as non-static to make sure we get correct locale
@BindingAdapter("date")
fun dateToString(view: TextView, date: Date?) {
    view.text = if (date != null) {
        SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(date)
    } else {
        ""
    }
}
