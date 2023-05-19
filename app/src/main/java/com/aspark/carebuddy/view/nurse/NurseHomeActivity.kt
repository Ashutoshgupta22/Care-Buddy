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

            finish()
        }

        viewModel.isNurseSignedIn.observe(this) {

            it?.let {
                setIsNurseSignedIn(it)
            }
        }

    }

    private fun setIsNurseSignedIn(b: Boolean) {

        val preferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val editor = preferences.edit()

        Log.i("nurseHomeActivity", "setNurseSignedIn: isSignedIn $b")

        editor.putBoolean("isNurseSignedIn", b)

        if (b)
            editor.putString("nurseEmail", Nurse.currentNurse.email)

        editor.apply()
    }
}