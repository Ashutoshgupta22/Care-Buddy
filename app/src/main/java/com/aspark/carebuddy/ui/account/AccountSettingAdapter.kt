package com.aspark.carebuddy.ui.account

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.aspark.carebuddy.databinding.ItemAccountSettingBinding
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.ui.login.LoginActivity

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

                setUserSignedOut()
                val intent = Intent(context, LoginActivity::class.java)
                //destroys all previous tasks
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)

            }
        }

    }

    private fun setUserSignedOut() {

        val preferences = context.getSharedPreferences(context.packageName,
            AppCompatActivity.MODE_PRIVATE)
        val editor = preferences.edit()

        editor.putBoolean("is_user_signed_in", false)
        editor.apply()

    }

    override fun getItemCount(): Int {

        return settingList.size
    }
}