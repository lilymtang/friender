package com.example.twitchandroidproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.FragmentHomeContainerBinding
import com.example.twitchandroidproject.ui.account.AccountFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeContainerFragment : Fragment() {

    private var homeFragment = HomeFragment()
    private var friendsFragment = FriendsFragment()
    private var accountFragment = AccountFragment()

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

        return binding.root
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