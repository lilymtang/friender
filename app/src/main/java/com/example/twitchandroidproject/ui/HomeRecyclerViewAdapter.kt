package com.example.twitchandroidproject.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.PersonCardBinding
import com.example.twitchandroidproject.repository.model.UserProfile
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class HomeRecyclerViewAdapter : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {
    lateinit var context: Context
    private lateinit var binding: PersonCardBinding
    var userProfiles = listOf<UserProfile>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(private val binding: PersonCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userProfile: UserProfile) {
            binding.person = userProfile
            binding.executePendingBindings()
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = PersonCardBinding.inflate(inflater)
        val holder =  ViewHolder(binding)

        holder.itemView.setOnClickListener(
            fun (v: View) {
                binding.root.findNavController().navigate(R.id.action_HomeFragment_to_HomeProfileFragment)
            }
        )

        context = viewGroup.context

        return holder
    }

    // Replace the contents of the view holder with data at given position
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val userProfile = userProfiles[position]
        viewHolder.bind(userProfile)

        val chipGroup: ChipGroup = binding.preferredInterestsChipgroup
        chipGroup.chipSpacingVertical = context.resources.getDimensionPixelSize(R.dimen.chip_vert_padding)

        // For each preferred interest in list, create a preferred interest chip
        for (preferredInterest in userProfile.preferredInterests) {
            chipGroup.addView(createPreferredInterestChip(preferredInterest))
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = userProfiles.size

    private fun createPreferredInterestChip(interest: String): TextView {
        val interestBadge = Chip(context)
        interestBadge.text = interest
        interestBadge.setEnsureMinTouchTargetSize(false) // Sets minimum padding of chip to 0
        return interestBadge
    }
}