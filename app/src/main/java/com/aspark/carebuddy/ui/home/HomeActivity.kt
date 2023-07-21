package com.aspark.carebuddy.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aspark.carebuddy.R
import com.aspark.carebuddy.databinding.ActivityHomeBinding
import com.aspark.carebuddy.map.MapActivity
import com.aspark.carebuddy.model.User.Companion.currentUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentUser.email?.let {
            Log.d("HomeActivity", "onCreate: getting user data ")
            viewModel.getUserdata(it)
        }

        viewModel.getSelfCare()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

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
            Log.e("UserHomeActivity", "onCreate: Checked notification " +
                    "permission was denied " )
            //showNotificationPermissionDialog()
            showPermissionDialog()
        }

        //check if location permission granted
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            Log.i("UserHomeActivity", "onCreate: Checked location permission granted")

        else {
            Log.e("UserHomeActivity", "onCreate: Checked location permission was denied ")
            showPermissionDialog()
        }

        viewModel.showToast.observe(this) {
            it?.let {
                showToast(it)
            }
        }
    }

//    private fun showNotificationPermissionDialog(){
//
//        val notificationPermissionRequest = registerForActivityResult(
//            ActivityResultContracts.RequestPermission()){ isGranted: Boolean ->
//
//            if (isGranted){
//
//                Log.d("UserHomeActivity", "showNotificationPermissionDialog: " +
//                        "notification permission granted ")
//
//            }
//            else{
//
//                Log.e("UserHomeActivity", "showNotificationPermissionDialog: " +
//                        "notification permission denied")
//            }
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            // no need to ask notification permission for android version below 13
//           // notificationPermissionRequest.launch(Manifest.permission.POST_NOTIFICATIONS)
//        }
//
//    }

    private fun showPermissionDialog() {

        //can not ask two different permissions separately.
        //hence location and notification permission both dialog are requested at same time

        val permissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { result: Map<String, Boolean?> ->

            val fineLocationGranted =
                result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)

            val coarseLocationGranted =
                result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)

            val notificationGranted =
                result.getOrDefault(Manifest.permission.POST_NOTIFICATIONS, false)

            if (fineLocationGranted != null && fineLocationGranted) {

                Log.d("UserHomeActivity", "onCreate: fine Location Granted ")
                val intent = Intent(this, MapActivity::class.java)
                startActivity(intent)
                finish()

            } else if (coarseLocationGranted != null && coarseLocationGranted)
                Log.d("UserHomeActivity", "onCreate: Coarse Location Granted ")

            else if( notificationGranted != null && notificationGranted) {
                Log.d("UserHomeActivity", "showLocationPermissionDialog: " +
                        "Notification granted")
            }

            else Log.d("UserHomeActivity", "onCreate: Location Permission Denied ")

        }

        permissionRequest.launch(
            arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }

    private fun showToast(msg: String) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


}