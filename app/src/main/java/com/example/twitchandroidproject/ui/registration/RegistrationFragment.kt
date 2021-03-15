package com.example.twitchandroidproject.ui.registration

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.FragmentRegistrationBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegistrationViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.getStartedButton.setOnClickListener {
            // TODO: navigate to screen where originally we opened login flow
        }

        binding.editProfileImage.setOnClickListener {
            // TODO: implement logic for selecting profile image
        }

        binding.dateOfBirthField.apply {
            // disable editing text field manually
            // as we are entering data via date picker
            inputType = InputType.TYPE_NULL
            keyListener = null

            // set both onClick and onTouch listener so that date picker would show up
            // in case if field was initially selected or already selected and we want to change value
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    openDatePicker()
                }
            }

            setOnClickListener {
                openDatePicker()
            }
        }

        viewModel.eventRegistrationSuccessful.observe(viewLifecycleOwner, { shouldNavigate ->
            shouldNavigate?.let {
                // TODO: Navigate back to Home screen

                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                viewModel.markEventRegistrationSuccessfulHandled()
            }
        })

        viewModel.registrationFormError.observe(viewLifecycleOwner, { errorMessage ->
            errorMessage?.let {
                Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_LONG).show()
            }
        })

        return binding.root
    }

    private fun openDatePicker() {

        val date = viewModel.dateOfBirth.value ?: Date()

        val calendar = Calendar.getInstance()
        calendar.time = date

        val initialYear = calendar[Calendar.YEAR]
        val initialMonth = calendar[Calendar.MONTH]
        val initialDay = calendar[Calendar.DAY_OF_MONTH]

        DatePickerDialog(
            requireContext(),
            { view, selectedYear, selectedMonth, selectedDay ->
                // set into text view
                calendar.set(selectedYear, selectedMonth, selectedDay)

                viewModel.dateOfBirth.value = calendar.time
            },
            initialYear,
            initialMonth,
            initialDay
        ).show()
    }
}