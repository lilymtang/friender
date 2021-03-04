package com.example.twitchandroidproject.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.twitchandroidproject.R
import com.example.twitchandroidproject.databinding.HomeProfileFragmentBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeProfileFragment : Fragment(), View.OnClickListener {
    lateinit var fab: ExtendedFloatingActionButton
    private var _binding: HomeProfileFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = HomeProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab = binding.addFriendFab
        fab.setOnClickListener(this)

        //TODO: make call to database to get actual data
    }

    override fun onClick(v: View) {
        //TODO: Implement button behavior
        Toast.makeText(context, "Execute action", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}