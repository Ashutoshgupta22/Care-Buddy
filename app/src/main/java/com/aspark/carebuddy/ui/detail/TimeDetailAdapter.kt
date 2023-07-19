package com.aspark.carebuddy.ui.detail

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aspark.carebuddy.R
import com.aspark.carebuddy.databinding.ItemSpecialitiesDetailBinding
import com.aspark.carebuddy.databinding.ItemTimeDetailBinding

class TimeDetailAdapter(private val timeList: ArrayList<String>):
    RecyclerView.Adapter<TimeDetailAdapter.ViewHolder>() {

    var selectedPosition = -1
    lateinit var context : Context

    class ViewHolder(binding: ItemTimeDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val tvTime = binding.tvTimeDetail
        val cvTime = binding.cvTimeDetail

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        context = parent.context
        val binding = ItemTimeDetailBinding.inflate(
            LayoutInflater.from(context),
            parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.d("TimeDetailAdapter", "onBindViewHolder: called")


        // holder.tvTime.text = timeList[position]

        holder.itemView.setOnClickListener {

            selectedPosition = position
            Log.d("TimeDetailAdapter", "onBindViewHolder: selectedPos $selectedPosition")
        }

        if (selectedPosition == position) {
            Log.d("TimeDetailAdapter", "onBindViewHolder: selectedPos == position")

            holder.cvTime.backgroundTintList = ColorStateList.valueOf(
                context.resources.getColor(R.color.primary_blue, context.theme))
            holder.tvTime.setTextColor(Color.WHITE)
        }

        else {
            holder.cvTime.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            holder.tvTime.setTextColor(ColorStateList.valueOf(Color.GRAY))

        }


    }

    override fun getItemCount(): Int {

        return 6
    }
}