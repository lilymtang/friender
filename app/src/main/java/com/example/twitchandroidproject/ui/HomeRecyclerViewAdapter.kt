package com.example.twitchandroidproject.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.PersonCardBinding
import com.example.twitchandroidproject.repository.model.UserProfile
import com.google.android.flexbox.FlexboxLayout

class HomeRecyclerViewAdapter() : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {
    lateinit var context: Context
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

        context = viewGroup.context

        return holder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        var userProfile = userProfiles[position]
        viewHolder.bind(userProfile)
        viewHolder.itemView.setHasTransientState(true)
        for (preferredInterest in userProfile.preferredInterests) {
            viewHolder.itemView.findViewById<FlexboxLayout>(R.id.preferred_interests_layout).addView(createInterestBadge(preferredInterest))
        }
        viewHolder.itemView.setHasTransientState(false)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = userProfiles.size

    private fun createInterestBadge(interest: String): TextView {
        val interestBadge = TextView(context)
        interestBadge.text = interest
        interestBadge.setTextAppearance(R.style.TextAppearance_MaterialComponents_Caption)
        interestBadge.setTextColor(ContextCompat.getColor(context, R.color.white))
        interestBadge.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        Log.d("TAG_", context.resources.getDimensionPixelSize(R.dimen.badge_horiz_padding).toString())
        interestBadge.setPadding(
            context.resources.getDimensionPixelSize(R.dimen.badge_horiz_padding),
            context.resources.getDimensionPixelSize(R.dimen.badge_vert_padding),
            context.resources.getDimensionPixelSize(R.dimen.badge_horiz_padding),
            context.resources.getDimensionPixelSize(R.dimen.badge_vert_padding))
        interestBadge.setBackgroundResource(R.drawable.badge)
        return interestBadge
    }

}