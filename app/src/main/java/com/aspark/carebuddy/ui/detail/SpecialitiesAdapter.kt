package com.aspark.carebuddy.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aspark.carebuddy.databinding.ItemNurseServiceTagBinding
import com.aspark.carebuddy.databinding.ItemSpecialitiesDetailBinding

class SpecialitiesAdapter(private val specialitiesList: ArrayList<String>):
    RecyclerView.Adapter<SpecialitiesAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemSpecialitiesDetailBinding):
        RecyclerView.ViewHolder(binding.root) {

        val tvSpecialities = binding.tvSpecialitiesItem

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = ItemSpecialitiesDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvSpecialities.text = specialitiesList[position]
    }

    override fun getItemCount(): Int {

        return specialitiesList.size
    }
}