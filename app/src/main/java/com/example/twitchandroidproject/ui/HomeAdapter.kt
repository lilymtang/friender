package com.example.twitchandroidproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchandroidproject.R

class HomeAdapter(private val people: Array<Person>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val personName: TextView
        val personAge: TextView
        val preferredInterest1: TextView
        val preferredInterest2: TextView
        val preferredInterest3: TextView
        val personDistance: TextView

        init {
            personName = view.findViewById(R.id.person_name)
            personAge = view.findViewById(R.id.person_age)
            preferredInterest1 = view.findViewById(R.id.preferred_interest_1)
            preferredInterest2 = view.findViewById(R.id.preferred_interest_2)
            preferredInterest3 = view.findViewById(R.id.preferred_interest_3)
            personDistance = view.findViewById(R.id.person_distance)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.person_card, viewGroup, false)
        val holder =  ViewHolder(view)

        holder.itemView.setOnClickListener(
            fun (v: View) {
                view.findNavController().navigate(R.id.action_HomeFragment_to_HomeProfileFragment)
            }
        );

        return holder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.personAge.text = people[position].age
        viewHolder.personName.text = people[position].name
        viewHolder.personDistance.text = people[position].distance
        viewHolder.preferredInterest1.text = people[position].preferredInterest1
        viewHolder.preferredInterest2.text = people[position].preferredInterest2
        viewHolder.preferredInterest3.text = people[position].preferredInterest3
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = people.size

}