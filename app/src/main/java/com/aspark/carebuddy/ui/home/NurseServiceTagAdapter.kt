package com.aspark.carebuddy.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aspark.carebuddy.databinding.ItemNurseServiceTagBinding

class NurseServiceTagAdapter(private val serviceList: ArrayList<String>):
    RecyclerView.Adapter<NurseServiceTagAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemNurseServiceTagBinding): RecyclerView.ViewHolder(binding.root) {

        val tvServiceTag = binding.tvNurseService

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = ItemNurseServiceTagBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvServiceTag.text = serviceList[position]
    }

    override fun getItemCount(): Int {

        return serviceList.size
    }
}