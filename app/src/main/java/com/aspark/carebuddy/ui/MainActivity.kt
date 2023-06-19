package com.aspark.carebuddy.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aspark.carebuddy.databinding.ActivityMainBinding
import com.aspark.carebuddy.ui.login.NurseLoginActivity
import com.aspark.carebuddy.ui.login.UserLoginActivity
import com.aspark.carebuddy.model.Nurse.Companion.currentNurse
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.ui.home.NurseHomeActivity
import com.aspark.carebuddy.ui.home.UserHomeActivity

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

            val intent = Intent(this, UserHomeActivity::class.java)
            startActivity(intent)

        } else if (isNurseSignedIn) {

            val nurseEmail = preferences.getString("nurseEmail", null)
            Log.d("MainActivity", "onCreate: currentNurse: $nurseEmail")
            currentNurse.email = nurseEmail!!

            val intent = Intent(this, NurseHomeActivity::class.java)
            startActivity(intent)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nurseCard.setOnClickListener {
            val intent = Intent(this, NurseLoginActivity::class.java)
            startActivity(intent)
        }

        binding.userCard.setOnClickListener {
            val intent = Intent(this, UserLoginActivity::class.java)
            startActivity(intent)
        }

    }

//    override fun destroyActivity() {
//        finish()
//        Log.i("MainActivity", "destroyActivity: Activity Destroyed!")
//    }
}