package com.example.twitchandroidproject.ui.account

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
import com.example.twitchandroidproject.databinding.FragmentAccountBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date


@AndroidEntryPoint
class AccountFragment : Fragment() {

    lateinit var binding: FragmentAccountBinding
    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)

//        binding.loginButton.setOnClickListener { v: View ->
//            v.findNavController()
//                .navigate(AccountFragmentDirections.actionAccountFragmentToLoginFragment())
//        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

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

        // subscribe to force viewmodel to start loading required data
        viewModel.currentUserProfile.observe(viewLifecycleOwner) {
            // we don't need to do anything extra here as data is mapped via databinding
        }


        // Showing a toast when the user saves account changes
        viewModel.saveMessage.observe(viewLifecycleOwner, { saveSuccessfulMessage ->
            saveSuccessfulMessage?.let {
                Snackbar.make(requireView(), saveSuccessfulMessage, Snackbar.LENGTH_LONG).show()

                viewModel.markSaveMessageDisplayedHandled()
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
            { _, selectedYear, selectedMonth, selectedDay ->
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