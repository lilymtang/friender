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

    private var currentlyDisplayedFragment: Fragment? = null

    private val viewModel: HomeContainerViewModel by viewModels()

    lateinit var binding: FragmentHomeContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeContainerBinding.inflate(inflater, container, false)

        // add created fragments to homeNavViewItemContainer
        // to allow switching between them later w/o recreating them on switch
        // note we are checking currentlyDisplayedFragment to avoid re-adding
        // fragments on configuration change
        if (currentlyDisplayedFragment == null) {
            childFragmentManager.commit {
                add(R.id.homeNavViewItemContainer, accountFragment)
                detach(accountFragment)

                add(R.id.homeNavViewItemContainer, friendsFragment)
                detach(friendsFragment)

                add(R.id.homeNavViewItemContainer, homeFragment)
                currentlyDisplayedFragment = homeFragment
            }
        }

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

        // when switching between fragments use attach / detach to avoid recreating fragments
        // per https://developer.android.com/guide/fragments/lifecycle
        // fragments should not be reused if replace() is called
        childFragmentManager.commit {
            setReorderingAllowed(true)

            currentlyDisplayedFragment?.let { detach(it) }

            attach(fragmentToDisplay)
            currentlyDisplayedFragment = fragmentToDisplay
        }
    }
}