package com.aspark.carebuddy.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
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

        val ivNursePic = binding.ivTopNursePic
        val tvNurseName = binding.tvTopNurseName
        val tvNurseRating = binding.tvRating
        val tvNurseQualifications = binding.tvTopNurseQualification
        val rvServiceTags = binding.rvTopNurseServices
        val cvTopNurse = binding.cvTopNurses

                init {

                    cvTopNurse.setOnClickListener {

                        Log.d("TopNurseAdapter", "Top nurse card clicked")
                        val navController = binding.root.findNavController()
                        val action = HomeFragDirections.actionHomeFragToSecondActivity()
                        navController.navigate(action)
                    }
                }
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

        holder.tvNurseName.text = "${nurseList[position].firstName} ${nurseList[position].lastName}"
        holder.tvNurseRating.text = nurseList[position].rating.toString()
        holder.tvNurseQualifications.text = nurseList[position].qualifications

        holder.rvServiceTags.apply {
            val flexboxLayoutManager = FlexboxLayoutManager(context, FlexDirection.ROW,
                FlexWrap.WRAP)
            flexboxLayoutManager.justifyContent = JustifyContent.FLEX_START

            layoutManager = flexboxLayoutManager
            adapter = NurseServiceTagAdapter(nurseList[position].specialities)
        }
    }

    override fun getItemCount(): Int {
        return nurseList.size
    }
}