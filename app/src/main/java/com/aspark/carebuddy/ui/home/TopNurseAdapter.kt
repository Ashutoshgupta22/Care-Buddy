package com.aspark.carebuddy.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aspark.carebuddy.databinding.ItemTopNursesBinding
import com.aspark.carebuddy.model.Nurse
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class TopNurseAdapter(private val nurseList: ArrayList<Nurse>):
    RecyclerView.Adapter<TopNurseAdapter.MyViewHolder>() {

    private lateinit var context: Context

    class MyViewHolder(binding: ItemTopNursesBinding) : RecyclerView.ViewHolder(binding.root) {

        val nursePic = binding.ivTopNursePic
        val nurseName = binding.tvTopNurseName
        val nurseRating = binding.tvRating
        val rvServiceTags = binding.rvTopNurseServices
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        context = parent.context
        val binding = ItemTopNursesBinding.inflate(LayoutInflater.from(context),
            parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.rvServiceTags.apply {
           val flexboxLayoutManager = FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP)
                flexboxLayoutManager.justifyContent = JustifyContent.FLEX_START

            layoutManager = flexboxLayoutManager
            adapter = NurseServiceTagAdapter(arrayListOf("Baby care","Alzheimer's","Post Surgery"))
        }

    }

    override fun getItemCount(): Int {
        return 4
    }
}