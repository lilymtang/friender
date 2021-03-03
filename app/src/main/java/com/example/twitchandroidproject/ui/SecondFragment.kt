package com.example.twitchandroidproject.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.twitchandroidproject.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), View.OnClickListener {
    lateinit var fab: ExtendedFloatingActionButton

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab = view.findViewById(R.id.add_friend_fab)
        fab.setOnClickListener(this)

        //TODO: make call to database to get actual data
    }

    override fun onClick(v: View) {
        //TODO: Implement button behavior
        Toast.makeText(context, "Execute action", Toast.LENGTH_SHORT).show()
    }
}