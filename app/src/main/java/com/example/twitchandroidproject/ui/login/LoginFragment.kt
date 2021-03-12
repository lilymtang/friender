package com.example.twitchandroidproject.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.eventLoginSuccessful.observe(viewLifecycleOwner, { shouldNavigate ->
            shouldNavigate?.let {
                // TODO: Navigate back to the screen from where login was initiated

                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                viewModel.markEventLoginSuccessfulHandled()
            }
        })

        return binding.root
    }
}