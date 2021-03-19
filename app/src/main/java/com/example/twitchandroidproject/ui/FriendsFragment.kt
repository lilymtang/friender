package com.example.twitchandroidproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.FriendsFragmentBinding
import com.example.twitchandroidproject.repository.model.UserProfile
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class FriendsFragment : Fragment(), HomeRecyclerViewAdapter.OnProfileClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FriendsRecyclerViewAdapter
    private lateinit var removeConfirmationSnackbar: Snackbar
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

        // Create snackbar with option to undo
        removeConfirmationSnackbar = Snackbar.make(
            binding.root, R.string.friend_remove_confirmation,
            Snackbar.LENGTH_LONG
        )

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                // Save values to enable undo
                val deletedFriend = adapter.userProfiles[viewHolder.adapterPosition]

                viewModel.removeFriend(adapter.userProfiles[viewHolder.adapterPosition])

                // Show snackbar with option to undo
                removeConfirmationSnackbar.setAction(R.string.snack_bar_undo) { v: View? ->
                    undoDelete(deletedFriend)
                }
                removeConfirmationSnackbar.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        viewModel.friendProfiles.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.userProfiles = it
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Dismiss snackbar if you leave the Friend tab
        removeConfirmationSnackbar.dismiss()

        _binding = null
    }

    override fun onProfileClick(position: Int) {
        val userId = viewModel.friendProfiles.value!![position].id

        binding.root.findNavController()
            .navigate(
                HomeContainerFragmentDirections.actionHomeContainerFragmentToProfileFragment(
                    userId
                )
            )
    }

    private fun undoDelete(deletedFriend: UserProfile) {
        viewModel.addFriend(deletedFriend)
    }
}