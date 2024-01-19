package com.aspark.carebuddy.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aspark.carebuddy.databinding.ItemSelfCareBinding
import com.aspark.carebuddy.model.SelfCareModel

class SelfCareAdapter(private val selfCareList: ArrayList<SelfCareModel>):
    RecyclerView.Adapter<SelfCareAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(binding: ItemSelfCareBinding) : RecyclerView.ViewHolder(binding.root) {

        val selfCarePic = binding.ivSelfCarePic
        val selfCareHeadline = binding.tvSelfCareHeadline
        val selfCareDescription = binding.tvSelfCareDescription
        val selfCareSource = binding.tvSelfCareSource

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        context = parent.context
        val binding = ItemSelfCareBinding.inflate(
            LayoutInflater.from(context),
            parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        Glide
//            .with(context)
//            .load(selfCareList[position].picUrl)
//            .centerCrop()
//            .into(holder.selfCarePic)
//
//        holder.selfCareHeadline.text = selfCareList[position].headLine
//        holder.selfCareDescription.text = selfCareList[position].description
//        holder.selfCareSource.text = selfCareList[position].source

    }

    override fun getItemCount(): Int {
        return 4
    }
}