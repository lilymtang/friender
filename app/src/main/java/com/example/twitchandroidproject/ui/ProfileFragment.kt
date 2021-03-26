package com.example.twitchandroidproject.ui
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.ProfileFragmentBinding
import com.example.twitchandroidproject.generated.callback.OnClickListener
import com.example.twitchandroidproject.repository.model.UserProfile
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class ProfileFragment : Fragment(), View.OnClickListener {
    private val viewModel: ProfileFragmentViewModel by viewModels()
    lateinit var smsClickListener: OnClickListener

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = ProfileFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.profileFragment = this

        if(!isPermissionGranted()) {
            binding.personDistance.visibility = View.GONE
        } else {
            viewModel.distance.observe(viewLifecycleOwner, Observer { distance ->
                binding.personDistance.text = String.format(resources.getString(R.string.distance_away_template), distance)
            })
        }

        viewModel.showAddFriendButton.observe(viewLifecycleOwner, Observer {showButton ->
            if(showButton) binding.profileFab.show()
            else binding.profileFab.hide()
        })

        viewModel.showSmsButton.observe(viewLifecycleOwner, Observer {showButton ->
            if(showButton) {
                binding.sendSmsFab.setOnClickListener {
                    startActivity(createSmsIntent(viewModel.userProfile.value!!))
                }
                binding.sendSmsFab.show()
            } else binding.sendSmsFab.hide()
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
        Snackbar.make(v, resources.getString(R.string.friend_add_confirmation), Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun createSmsIntent(userProfile: UserProfile): Intent {
        val smsIntent = Intent(Intent.ACTION_VIEW)
        smsIntent.putExtra("address", userProfile.phoneNumber)
        smsIntent.putExtra("sms_body",
            String.format(
                resources.getString(R.string.send_sms_template),
                userProfile.fullName, viewModel.currentUserProfile.value?.fullName
            )
        )
        smsIntent.data = Uri.parse("sms:")
        return smsIntent
    }

    private fun isPermissionGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }
}