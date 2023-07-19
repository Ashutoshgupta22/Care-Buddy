package com.aspark.carebuddy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.collection.arraySetOf
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.aspark.carebuddy.R
import com.aspark.carebuddy.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_second) as NavHost
        val navController = navHost.navController
        //val navController = findNavController(R.id.nav_host_second)
        val appBarConfiguration = AppBarConfiguration(arraySetOf(),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp)
        binding.toolbarDetailFrag.setupWithNavController(navController, appBarConfiguration)


    }
}