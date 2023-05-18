package com.aspark.carebuddy.view.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aspark.carebuddy.UserHomeViewModel
import com.aspark.carebuddy.databinding.ActivityUserHomeBinding
import com.aspark.carebuddy.map.MapActivity
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.view.MainActivity

class UserHomeActivity : AppCompatActivity() {

    var isUserSignedIn = true
    private lateinit var binding : ActivityUserHomeBinding
    private val viewModel: UserHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserSignedIn()

        //TODO currentUser is null when app is opened after destroying it.
        Log.i("UserHomeActivity", "onCreate: currentUser: " + User.currentUser)

        //check if location permission granted
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            Log.i("UserHomeActivity", "onCreate: Checked that permission granted")

        else {
            Log.e("UserHomeActivity", "onCreate: Checked that permission was denied " )
            showLocationPermissionDialog()

        }

        binding.signOutBtn.setOnClickListener {

            Log.w("UserHomeActivity", "onCreate: User Logged Out")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            isUserSignedIn = false
            setUserSignedIn()
            finish()
        }

//        binding.locationIcon.setOnClickListener(this)
//        binding.homeText.setOnClickListener(this)
//        binding.downArrowIcon.setOnClickListener(this)

        binding.bookServiceBtn.setOnClickListener {

            Log.d("UserHomeActivity", "bookService: button clicked ")
            viewModel.bookServiceClickListener()

        }
    }

    private fun showLocationPermissionDialog() {

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { result: Map<String, Boolean?> ->

            val fineLocationGranted =
                result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)

            val coarseLocationGranted =
                result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)

            if (fineLocationGranted != null && fineLocationGranted) {

                Log.d("UserHomeActivity", "onCreate: fine Location Granted ")
                val intent = Intent(this, MapActivity::class.java)
                startActivity(intent)
                finish()

            } else if (coarseLocationGranted != null && coarseLocationGranted)
                Log.d("UserHomeActivity", "onCreate: Coarse Location Granted ")

            else Log.d("UserHomeActivity", "onCreate: Location Permission Denied ")

        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }

    private fun setUserSignedIn() {

        val preferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val editor = preferences.edit()

        Log.i("UserHomeActivity", "setUserSignedIn: isUserSignedIn $isUserSignedIn")
        editor.putBoolean("isUserSignedIn", isUserSignedIn)

        if (isUserSignedIn) editor.putString("UserEmail", User.currentUser?.email)
        else editor.putString("UserEmail", null)

        editor.apply()
    }
}