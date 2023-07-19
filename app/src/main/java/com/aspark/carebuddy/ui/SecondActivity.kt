package com.aspark.carebuddy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aspark.carebuddy.R
import com.aspark.carebuddy.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}