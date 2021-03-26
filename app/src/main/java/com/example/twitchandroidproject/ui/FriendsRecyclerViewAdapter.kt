package com.example.twitchandroidproject.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchandroidproject.databinding.FriendRowBinding
import com.example.twitchandroidproject.repository.model.UserProfile

class FriendsRecyclerViewAdapter(var onProfileClickListener: HomeRecyclerViewAdapter.OnProfileClickListener) : RecyclerView.Adapter<FriendsRecyclerViewAdapter.ViewHolder>() {
    lateinit var context: Context
    private lateinit var binding: FriendRowBinding

    var userProfiles = listOf<UserProfile>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: View,
                     val binding: FriendRowBinding,
                     private val onProfileClickListener: HomeRecyclerViewAdapter.OnProfileClickListener)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        // Set click listener to this class
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(userProfile: UserProfile) {
            binding.person = userProfile
            binding.executePendingBindings()
        }

        // onClick for this class will call onProfileClick and pass adapterPosition
        override fun onClick(v: View) {
            onProfileClickListener.onProfileClick(adapterPosition)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = FriendRowBinding.inflate(inflater)
        val holder = ViewHolder(binding.root, binding, onProfileClickListener)

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