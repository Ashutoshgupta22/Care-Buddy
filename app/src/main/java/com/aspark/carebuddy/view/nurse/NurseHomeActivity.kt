package com.aspark.carebuddy.view.nurse

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aspark.carebuddy.databinding.ActivityNurseHomeBinding
import com.aspark.carebuddy.model.Nurse
import com.aspark.carebuddy.model.NurseHomeViewModel

class NurseHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNurseHomeBinding
    private val viewModel : NurseHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNurseHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.signInNurse()

        binding.btnSignOut.setOnClickListener {

            viewModel.signOutNurse()
        }

        viewModel.isNurseSignedIn.observe(this) {

            it?.let {
                setNurseSignedIn()
            }
        }

    }

    private fun setNurseSignedIn() {

        val preferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val editor = preferences.edit()

        Log.i("nurseHomeActivity", "setNurseSignedIn: isSignedIn ${viewModel.isNurseSignedIn}")

        viewModel.isNurseSignedIn.value?.let { editor.putBoolean("isNurseSignedIn", it) }

        if (viewModel.isNurseSignedIn.value == true)
            editor.putString("NurseUsername", Nurse.currentNurse.email)
        else
            editor.putString("NurseUsername", null)

        editor.apply()
    }
}