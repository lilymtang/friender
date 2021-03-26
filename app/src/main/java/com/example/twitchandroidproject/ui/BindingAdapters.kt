package com.example.twitchandroidproject.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.ui.utils.getColorFromAttr
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale

class BindingAdapters {
    companion object {
        @BindingAdapter("preferredInterests")
        @JvmStatic
        fun populatePreferredInterests(chipGroup: ChipGroup, preferredInterests: List<String>?) {
            // LiveData is null initially because of async Room call in ProfileFragmentViewModel
            if (preferredInterests == null) {
                return
            }

            chipGroup.chipSpacingVertical =
                chipGroup.context.resources.getDimensionPixelSize(R.dimen.chip_vert_padding)
            chipGroup.removeAllViews()

            for (interest in preferredInterests) {
                chipGroup.addView(
                    createInterestChip(
                        chipGroup.context,
                        interest,
                        chipGroup.context.getColorFromAttr(R.attr.preferredInterestColor),
                        chipGroup.context.getColorFromAttr(R.attr.interestTextColor)
                    )
                )
            }
        }

        @BindingAdapter("interests")
        @JvmStatic
        fun populateInterests(chipGroup: ChipGroup, interests: List<String>?) {
            // LiveData is null initially because of async Room call in ProfileFragmentViewModel
            if (interests == null) {
                return
            }

            for (interest in interests) {
                chipGroup.addView(
                    createInterestChip(
                        chipGroup.context,
                        interest,
                        chipGroup.context.getColorFromAttr(R.attr.interestColor),
                        chipGroup.context.getColorFromAttr(R.attr.interestTextColor)
                    )
                )
            }
        }

        private fun createInterestChip(
            context: Context,
            interest: String,
            chipColor: Int,
            chipTextColor: Int
        ): TextView {
            val interestBadge = Chip(context)
            interestBadge.text = interest
            interestBadge.setTextColor(ColorStateList.valueOf(chipTextColor))
            interestBadge.setEnsureMinTouchTargetSize(false) // Sets minimum padding of chip to 0
            interestBadge.chipBackgroundColor = ColorStateList.valueOf(chipColor)
            return interestBadge
        }

        @BindingAdapter("dateOfBirth")
        @JvmStatic
        fun dateOfBirthToAge(view: TextView, dateOfBirth: Date?) {
            // LiveData is null initially because of async Room call in ProfileFragmentViewModel
            if (dateOfBirth == null) {
                view.text = Date().toString()
                return
            }

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
            imageView.apply {
                if (profileImage != null) {
                    setImageBitmap(profileImage)
                } else {
                    // set placeholder image
                    setImageDrawable(
                        ContextCompat.getDrawable(
                            imageView.context,
                            R.drawable.avatar
                        )
                    )
                }
            }
        }

        @BindingAdapter("availableInterests")
        @JvmStatic
        fun setInterests(
            autoCompleteTextView: AutoCompleteTextView,
            availableInterests: List<String>?
        ) {
            if (availableInterests != null) {
                val adapter = ArrayAdapter(
                    autoCompleteTextView.context,
                    R.layout.interest_item,
                    availableInterests
                )
                autoCompleteTextView.setAdapter(adapter)
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
