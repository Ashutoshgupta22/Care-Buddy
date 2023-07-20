package com.aspark.carebuddy.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aspark.carebuddy.databinding.FragmentAccountBinding

class AccountFrag: Fragment() {

    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.rvAccountSetting.apply {

            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = AccountSettingAdapter(arrayListOf("Patient profile", "Previous bookings",
                "Logout"))

        }


    }
}