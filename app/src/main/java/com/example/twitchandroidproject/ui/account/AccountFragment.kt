package com.example.twitchandroidproject.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AccountFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val binding: FragmentAccountBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        binding.loginButton.setOnClickListener { v: View ->
            v.findNavController()
                .navigate(AccountFragmentDirections.actionAccountFragmentToLoginFragment())
        }

        return binding.root
    }

}