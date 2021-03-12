package com.example.twitchandroidproject.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.FriendRowBinding
import com.example.twitchandroidproject.repository.model.UserProfile

class FriendsRecyclerViewAdapter : RecyclerView.Adapter<FriendsRecyclerViewAdapter.ViewHolder>() {
    lateinit var context: Context
    private lateinit var binding: FriendRowBinding
    var userProfiles = listOf<UserProfile>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(private val binding: FriendRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userProfile: UserProfile) {
            binding.person = userProfile
            binding.executePendingBindings()
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = FriendRowBinding.inflate(inflater)
        val holder =  ViewHolder(binding)

        holder.itemView.setOnClickListener(
            fun (v: View) {
                binding.root.findNavController().navigate(R.id.action_FriendsFragment_to_HomeProfileFragment)
            }
        )

        context = viewGroup.context

        return holder
    }

    // Replace the contents of the view holder with data at given position
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val userProfile = userProfiles[position]
        viewHolder.bind(userProfile)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = userProfiles.size

}