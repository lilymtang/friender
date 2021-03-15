package com.example.twitchandroidproject.ui
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.twitchandroidproject.databinding.ProfileFragmentBinding
import com.example.twitchandroidproject.repository.model.UserProfile
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var fab: ExtendedFloatingActionButton
    private var userId: Long? = null
    private val viewModel: ProfileFragmentViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = ProfileFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        fab = binding.profileFab
        fab.setOnClickListener(this)

        viewModel.getUserProfile().observe(viewLifecycleOwner, Observer { userProfile ->
            when(userProfile.userProfileType) {
                UserProfile.UserProfileType.OTHER -> fab.show()
                UserProfile.UserProfileType.FRIEND -> fab.show()
                else -> fab.hide()
            }
        })

        return binding.root
    }

    override fun onClick(v: View) {
        //TODO: Implement button behavior
    }


}