package com.example.twitchandroidproject.ui
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.ProfileFragmentBinding
import com.example.twitchandroidproject.repository.model.UserProfile
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.ProcessingInstruction

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var fab: ExtendedFloatingActionButton
    private var userId: Long? = null
    private val viewModel: ProfileFragmentViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = ProfileFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        fab = binding.profileFab
        fab.setOnClickListener(this)

        viewModel.getUserProfile().observe(viewLifecycleOwner, Observer { userProfile ->
            when(userProfile.userProfileType) {
                UserProfile.UserProfileType.OTHER -> fab.show()
                UserProfile.UserProfileType.FRIEND -> fab.show()
                else -> fab.hide()
            }

            addChipGroup(binding, userProfile)
        })

        return binding.root
    }

    override fun onClick(v: View) {
        //TODO: Implement button behavior
    }

    private fun addChipGroup(binding: ProfileFragmentBinding, userProfile: UserProfile) {
        val chipGroup: ChipGroup = binding.profileInterestsChipgroup
        chipGroup.chipSpacingVertical = requireContext().resources.getDimensionPixelSize(R.dimen.chip_vert_padding)

        // For each preferred interest in list, create a preferred interest chip
        for (preferredInterest in userProfile.preferredInterests) {
            chipGroup.addView(createInterestChip(preferredInterest,
                requireContext().getColorFromAttr(R.attr.preferredInterestColor)
            ))
        }

        // For each interest in list, create an interest chip
        for (preferredInterest in userProfile.interests) {
            chipGroup.addView(createInterestChip(preferredInterest,
                requireContext().getColorFromAttr(R.attr.interestColor)
            ))
        }
    }

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