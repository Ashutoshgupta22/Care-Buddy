package com.aspark.carebuddy.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aspark.carebuddy.MainViewModel
import com.aspark.carebuddy.databinding.ActivityMainBinding
import com.aspark.carebuddy.login.NurseLoginActivity
import com.aspark.carebuddy.login.UserLoginActivity
import com.aspark.carebuddy.model.Nurse.Companion.currentNurse
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.view.nurse.NurseHomeActivity
import com.aspark.carebuddy.view.user.UserHomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val isUserSignedIn = preferences.getBoolean("isUserSignedIn", false)
        val isNurseSignedIn = preferences.getBoolean("isNurseSignedIn", false)

        //TODO to get all the user data send userEmail to backend and get user object in return

        if (isUserSignedIn) {

            val userEmail = preferences.getString("userEmail", null)
            Log.d("MainActivity", "onCreate: currentUser: $userEmail")

            User.currentUser.email = userEmail
            viewModel.callUserHomeActivity()

        } else if (isNurseSignedIn) {

            val nurseEmail = preferences.getString("nurseEmail", null)
            Log.d("MainActivity", "onCreate: currentNurse: $nurseEmail")
            currentNurse.email = nurseEmail!!
            viewModel.callNurseHomeActivity()
        }

        viewModel.startUserHomeActivity.observe(this){

            it?.let {
                val intent = Intent(this,UserHomeActivity::class.java)
                startActivity(intent)
            }
        }

        viewModel.startNurseHomeActivity.observe(this){

            it?.let {
                val intent = Intent(this,NurseHomeActivity::class.java)
                startActivity(intent)
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nurseCard.setOnClickListener {
            viewModel.callNurseLoginActivity()
        }

        binding.userCard.setOnClickListener {
            viewModel.callUserLoginActivity()
        }

        viewModel.startNurseLoginActivity.observe(this){

            it?.let {
                val intent = Intent(this,NurseLoginActivity::class.java)
                startActivity(intent)
            }
        }

        viewModel.startUserLoginActivity.observe(this){

            it?.let {
                val intent = Intent(this,UserLoginActivity::class.java)
                startActivity(intent)
            }
        }

    }

//    override fun destroyActivity() {
//        finish()
//        Log.i("MainActivity", "destroyActivity: Activity Destroyed!")
//    }
}