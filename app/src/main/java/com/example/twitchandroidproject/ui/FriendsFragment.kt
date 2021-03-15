package com.example.twitchandroidproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.FriendsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class FriendsFragment : Fragment(), HomeRecyclerViewAdapter.OnProfileClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FriendsRecyclerViewAdapter
    private var _binding: FriendsFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: FriendsFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FriendsFragmentBinding.inflate(inflater, container, false)

        // Configure recycler view and adapter
        recyclerView = binding.friendsRecycler
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FriendsRecyclerViewAdapter(this)
        recyclerView.adapter = adapter

        viewModel.friendProfiles.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.userProfiles = it
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProfileClick(position: Int) {
        val userIdBundle = bundleOf("userId" to viewModel.friendProfiles.value!![position].id)
        binding.root.findNavController().navigate(R.id.action_FriendsFragment_to_ProfileFragment, userIdBundle)
    }
}