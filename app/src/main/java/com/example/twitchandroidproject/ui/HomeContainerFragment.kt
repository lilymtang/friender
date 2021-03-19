package com.example.twitchandroidproject.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.FragmentHomeContainerBinding
import com.example.twitchandroidproject.ui.account.AccountFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeContainerFragment : Fragment() {

    @Inject
    lateinit var homeFragment: HomeFragment

    @Inject
    lateinit var friendsFragment: FriendsFragment

    @Inject
    lateinit var accountFragment: AccountFragment

    private val viewModel: HomeContainerViewModel by viewModels()

    lateinit var binding: FragmentHomeContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeContainerBinding.inflate(inflater, container, false)

        // manually switching between tabs in home container
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            val selectedFragment = getFragmentBySelectedItemId(item.itemId)
            setNavItemView(selectedFragment)

            true
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showLogoutConfirmationDialog()
                }
            })

        viewModel.eventLogoutSuccessful.observe(viewLifecycleOwner, { shouldNavigate ->
            if (shouldNavigate) {
                findNavController().navigateUp()
                viewModel.markEventLogoutHandled()
            }
        })

        return binding.root
    }

    // Dialog for the user to confirm that user wants to logout
    private fun showLogoutConfirmationDialog() {

        val alertDialog = AlertDialog.Builder(context).apply {
            setMessage(R.string.dialog_message_logout)

            setPositiveButton(R.string.dialog_button_logout) { _, _ ->
                viewModel.logout()
            }

            setNegativeButton(R.string.dialog_button_cancel, null)
        }.create()

        alertDialog.show()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        // select tab to display based on restored information for bottom navigation:
        // navigation component will restore selected tab for bottom navigation view
        // so we are making sure we are setting corresponding top part
        val initialFragment =
            getFragmentBySelectedItemId(binding.bottomNavigationView.selectedItemId)

        setNavItemView(initialFragment)
    }

    private fun getFragmentBySelectedItemId(selectedItemId: Int) =
        when (selectedItemId) {
            R.id.HomeFragment -> homeFragment
            R.id.FriendsFragment -> friendsFragment
            else -> accountFragment
        }

    // sets content for upper part of the view
    private fun setNavItemView(fragmentToDisplay: Fragment) {
        childFragmentManager.commit {
            setReorderingAllowed(true)

            replace(R.id.homeNavViewItemContainer, fragmentToDisplay)
        }
    }
}