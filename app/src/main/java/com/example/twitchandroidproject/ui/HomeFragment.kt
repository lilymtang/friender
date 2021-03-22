package com.example.twitchandroidproject.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchandroidproject.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment @Inject constructor() : Fragment(),
    HomeRecyclerViewAdapter.OnProfileClickListener,
    SearchView.OnQueryTextListener,
    SearchView.OnCloseListener
{
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeRecyclerViewAdapter
    private lateinit var searchView: SearchView
    var isCollapsed = true
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

        // Configure search view
        searchView = binding.searchBar
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)
        searchView.isIconified = isCollapsed // remove focus and collapse search bar

        // Add observer to user profiles dataset and set adapter data when it changes
        viewModel.userProfiles.observe(viewLifecycleOwner, Observer {
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
        val userId = viewModel.userProfiles.value!![position].id

        binding.root.findNavController()
            .navigate(
                HomeContainerFragmentDirections.actionHomeContainerFragmentToProfileFragment(
                    userId
                )
            )
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        isCollapsed = false // query remains visible in the search bar
        viewModel.setSearchQuery(query)
        return false
    }

    /* Allows for live updating search */
    override fun onQueryTextChange(query: String?): Boolean {
        isCollapsed = false // query remains visible in the search bar
        viewModel.setSearchQuery(query)
        return false
    }

    override fun onClose(): Boolean {
        viewModel.setSearchQuery(null)
        searchView.onActionViewCollapsed()
        isCollapsed = true // remove focus and collapse search bar
        return true
    }

}