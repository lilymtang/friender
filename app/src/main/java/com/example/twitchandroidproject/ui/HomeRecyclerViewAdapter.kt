package com.example.twitchandroidproject.ui

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.PersonCardBinding
import com.example.twitchandroidproject.databinding.PersonCardBindingImpl
import com.example.twitchandroidproject.repository.model.UserProfile

class HomeRecyclerViewAdapter(viewModel: HomeFragmentViewModel) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {
    var userProfiles = listOf<UserProfile>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val binding: PersonCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userProfile: UserProfile) {
            binding.person = userProfile
            binding.executePendingBindings()
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = PersonCardBinding.inflate(inflater)
        val holder =  ViewHolder(binding)

        holder.itemView.setOnClickListener(
            fun (v: View) {
                binding.root.findNavController().navigate(R.id.action_HomeFragment_to_HomeProfileFragment)
            }
        )

        return holder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.bind(userProfiles[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = userProfiles.size

}