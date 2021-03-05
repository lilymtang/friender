package com.example.twitchandroidproject.ui

import MarginItemDecoration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


data class Person(
    val name: String,
    val age: String,
    val preferredInterest1: String,
    val preferredInterest2: String,
    val preferredInterest3: String,
    val distance: String
)

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: HomeRecyclerViewAdapter
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: make call to database to get actual data
        val data = arrayOf(Person("Lily", "24", "backpacking", "baking", "photography", "1 mile away"),
            Person("Milo", "24", "chicken pot pie", "Smasher", "sleeping", "41 miles away"),
            Person("Moni", "21", "reading", "art", "bungee jumping", "1 mile away"),
            Person("Reallylongname", "24", "yoga", "sewing", "skydiving", "1 mile away"),
            Person("Lily", "24", "woodworking", "glassblowing", "jewelry making", "1 mile away")
        )

        // Configure recycler view and adapter
        recyclerView = binding.homeRecycler
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.addItemDecoration(MarginItemDecoration(2, 50, includeEdge = true))

        adapter = HomeRecyclerViewAdapter(data)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}