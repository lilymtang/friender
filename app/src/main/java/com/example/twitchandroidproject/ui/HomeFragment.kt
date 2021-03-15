package com.example.twitchandroidproject.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment : Fragment(), HomeRecyclerViewAdapter.OnProfileClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeRecyclerViewAdapter
    private var _binding: HomeFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        // Configure recycler view and adapter
        recyclerView = binding.homeRecycler
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.addItemDecoration(MarginItemDecoration(2, 50, includeEdge = true))

        adapter = HomeRecyclerViewAdapter(this)
        recyclerView.adapter = adapter

        viewModel.getUserProfiles().observe(viewLifecycleOwner, Observer {
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
        val userIdBundle = bundleOf("userId" to viewModel.getUserProfiles().value!![position].id)
        Log.d("TAG", "Pass userId on click: " + userIdBundle)
        binding.root.findNavController().navigate(R.id.action_HomeFragment_to_ProfileFragment, userIdBundle)
    }
}