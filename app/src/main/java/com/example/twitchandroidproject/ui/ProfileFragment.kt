package com.example.twitchandroidproject.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.ProfileFragmentBinding
import com.example.twitchandroidproject.repository.model.UserProfile
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class ProfileFragment : Fragment(), View.OnClickListener {
    private val viewModel: ProfileFragmentViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = ProfileFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.profileFragment = this

        viewModel.userProfile.observe(viewLifecycleOwner, Observer { userProfile ->
            when(userProfile.userProfileType) {
                UserProfile.UserProfileType.OTHER -> binding.profileFab.show()
                else -> binding.profileFab.hide()
            }
        })

        // Set up toolbar with up action enabled
        val appCompatActivity: AppCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(findNavController())
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)


        return binding.root
    }

    override fun onClick(v: View) {
        viewModel.addFriend()
        Snackbar.make(v, R.string.friend_add_confirmation, Snackbar.LENGTH_SHORT)
            .show()
    }
}