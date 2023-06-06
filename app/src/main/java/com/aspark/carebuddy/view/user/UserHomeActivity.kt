package com.aspark.carebuddy.view.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aspark.carebuddy.UserHomeViewModel
import com.aspark.carebuddy.databinding.ActivityUserHomeBinding
import com.aspark.carebuddy.map.MapActivity
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.view.MainActivity

class UserHomeActivity : AppCompatActivity() {

    private var isUserSignedIn = true
    private lateinit var binding : ActivityUserHomeBinding
    private val viewModel: UserHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setIsUserSignedIn()

        Log.i("UserHomeActivity", "onCreate: currentUser: " + User.currentUser)

        //check if notification permission granted
        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
            Log.i("UserHomeActivity", "onCreate: Checked notification permission granted")

        else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)){

            //show a educational ui to inform the user of benefits of accepting this permission
            Toast.makeText(this, "Accept notification permission to not " +
                    "miss any update about your bookings",
                Toast.LENGTH_LONG).show()
        }

        else {
            Log.e("UserHomeActivity", "onCreate: Checked notification permission was denied " )
            showNotificationPermissionDialog()
        }

        //check if location permission granted
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            Log.i("UserHomeActivity", "onCreate: Checked location permission granted")

        else {
            Log.e("UserHomeActivity", "onCreate: Checked location permission was denied " )
            showLocationPermissionDialog()
        }

        binding.signOutBtn.setOnClickListener {

            Log.w("UserHomeActivity", "onCreate: User Logged Out")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            isUserSignedIn = false
            setIsUserSignedIn()
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

    private fun showNotificationPermissionDialog(){

        val notificationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){ isGranted: Boolean ->

            if (isGranted){

                Log.d("UserHomeActivity", "showNotificationPermissionDialog: " +
                        "notification permission granted ")

            }
            else{

                Log.e("UserHomeActivity", "showNotificationPermissionDialog: " +
                        "notification permission denied")
            }
        }

        notificationPermissionRequest.launch(Manifest.permission.POST_NOTIFICATIONS)
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

    private fun setIsUserSignedIn() {

        val preferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val editor = preferences.edit()

        Log.i("UserHomeActivity", "setIsUserSignedIn: isUserSignedIn $isUserSignedIn")
        editor.putBoolean("isUserSignedIn", isUserSignedIn)

        if (isUserSignedIn) editor.putString("userEmail", User.currentUser.email)
        else editor.putString("userEmail", null)

        editor.apply()
    }
}