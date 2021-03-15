package com.example.twitchandroidproject.ui

import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.PersonCardBinding
import com.example.twitchandroidproject.repository.model.UserProfile
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class HomeRecyclerViewAdapter(var onProfileClickListener: OnProfileClickListener) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {
    lateinit var context: Context
    private lateinit var binding: PersonCardBinding

    var userProfiles = listOf<UserProfile>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface OnProfileClickListener {
        fun onProfileClick(position: Int)
    }

    // ViewHolder implements click listener
    class ViewHolder(itemView: View,
                     private val binding: PersonCardBinding,
                     private val onProfileClickListener: OnProfileClickListener)
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
        binding = PersonCardBinding.inflate(inflater)
        val holder =  ViewHolder(binding.root, binding, onProfileClickListener)

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
            chipGroup.addView(createInterestChip(preferredInterest,
                context.getColorFromAttr(R.attr.preferredInterestColor)
            ))
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = userProfiles.size

    private fun createInterestChip(interest: String, chipColor: Int): TextView {
        val interestBadge = Chip(context)
        interestBadge.text = interest
        interestBadge.setEnsureMinTouchTargetSize(false) // Sets minimum padding of chip to 0
        interestBadge.chipBackgroundColor = ColorStateList.valueOf(chipColor)
        return interestBadge
    }

    @ColorInt
    fun Context.getColorFromAttr(
        @AttrRes attrColor: Int,
        typedValue: TypedValue = TypedValue(),
        resolveRefs: Boolean = true
    ): Int {
        theme.resolveAttribute(attrColor, typedValue, resolveRefs)
        return typedValue.data
    }
}