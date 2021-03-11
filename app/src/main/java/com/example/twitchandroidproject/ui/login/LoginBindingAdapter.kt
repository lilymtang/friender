package com.example.twitchandroidproject.ui.login

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

// Enables setting error message on TextInputLayout as it is not available as xml property
@BindingAdapter("errorMessage")
fun setErrorMessage(textInputLayout: TextInputLayout, errorMessage: String?) {
    textInputLayout.error = errorMessage
}