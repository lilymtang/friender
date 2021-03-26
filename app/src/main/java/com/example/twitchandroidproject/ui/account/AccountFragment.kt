package com.example.twitchandroidproject.ui.account

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.FragmentAccountBinding
import com.example.twitchandroidproject.ui.HomeContainerFragmentDirections
import com.example.twitchandroidproject.ui.utils.getColorFromAttr
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


@AndroidEntryPoint
class AccountFragment @Inject constructor() : Fragment() {

    lateinit var binding: FragmentAccountBinding
    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setHasOptionsMenu(true)

        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
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

        binding.preferredInterestsTextView.setOnItemClickListener { adapterView, _, position, _ ->

            val selectedItem = adapterView.getItemAtPosition(position).toString()
            viewModel.addPreferredInterest(selectedItem)

            binding.preferredInterestsTextView.setText("")
        }

        binding.interestsTextView.setOnItemClickListener { adapterView, _, position, _ ->

            val selectedItem = adapterView.getItemAtPosition(position).toString()
            viewModel.addInterest(selectedItem)

            binding.interestsTextView.setText("")
        }

        // creating chips from fragment instead of via binding adapter
        // to allow settings setOnCloseIconClickListener on each chip
        // (binding adapters do not support passing lambda functions from xml)

        viewModel.preferredInterests.observe(viewLifecycleOwner) { preferredInterests ->
            val chipGroup = binding.preferredInterestsChipGroup
            chipGroup.removeAllViews()

            if (preferredInterests != null) {

                val chipColor = ColorStateList.valueOf(
                    requireContext().getColorFromAttr(R.attr.preferredInterestColor)
                )
                val chipTextColor = requireContext().getColorFromAttr(R.attr.interestTextColor)

                for (preferredInterest in preferredInterests) {
                    val chip = Chip(chipGroup.context).apply {
                        text = preferredInterest

                        chipBackgroundColor = chipColor
                        setTextColor(chipTextColor)

                        isCloseIconVisible = true
                        closeIconTint = ColorStateList.valueOf(chipTextColor)

                        setOnCloseIconClickListener {
                            viewModel.removePreferredInterest(preferredInterest)
                        }
                    }

                    chipGroup.addView(chip)
                }
            }
        }

        viewModel.interests.observe(viewLifecycleOwner) { interests ->
            val chipGroup = binding.interestsChipGroup
            chipGroup.removeAllViews()

            if (interests != null) {
                val chipColor = ColorStateList.valueOf(
                    requireContext().getColorFromAttr(R.attr.interestColor)
                )
                val chipTextColor = requireContext().getColorFromAttr(R.attr.interestTextColor)

                for (interest in interests) {
                    val chip = Chip(chipGroup.context).apply {
                        text = interest

                        chipBackgroundColor = chipColor
                        setTextColor(chipTextColor)

                        isCloseIconVisible = true
                        closeIconTint = ColorStateList.valueOf(chipTextColor)

                        setOnCloseIconClickListener {
                            viewModel.removeInterest(interest)
                        }
                    }

                    chipGroup.addView(chip)
                }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_account, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_logout -> {
                viewModel.logout()
                viewModel.eventLogoutSuccessful.observe(viewLifecycleOwner, { shouldNavigate ->
                    if (shouldNavigate) {
                        findNavController().navigateUp()
                        viewModel.markEventLogoutHandled()
                    }
                })
            }
            R.id.action_view -> {
                findNavController().navigate(
                    HomeContainerFragmentDirections.actionHomeContainerFragmentToProfileFragment(
                        viewModel.currentUserProfile.value!!.id
                    )
                )
            }


        }
        return super.onOptionsItemSelected(item)
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