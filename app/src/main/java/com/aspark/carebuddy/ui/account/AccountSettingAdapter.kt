package com.aspark.carebuddy.ui.account

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aspark.carebuddy.databinding.ItemAccountSettingBinding

class AccountSettingAdapter(private val settingList: ArrayList<String>) :
    RecyclerView.Adapter<AccountSettingAdapter.ViewHolder>(){

    lateinit var context: Context

    class ViewHolder(binding: ItemAccountSettingBinding): RecyclerView.ViewHolder(binding.root) {

        val tvSetting = binding.tvItemAccountSetting

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        context = parent.context
        val binding = ItemAccountSettingBinding.inflate(LayoutInflater.from(context),
        parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvSetting.text = settingList[position]

        holder.tvSetting.setOnClickListener {
            if (position == settingList.size - 1) {

                Log.i("AccountSettingAdapter", "onBindViewHolder: Logout clicked ")


            }
        }

    }

    override fun getItemCount(): Int {

        return settingList.size
    }
}