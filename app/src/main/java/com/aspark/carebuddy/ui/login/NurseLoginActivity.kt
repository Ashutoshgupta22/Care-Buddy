package com.aspark.carebuddy.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.aspark.carebuddy.databinding.ActivityNurseLoginBinding
import com.aspark.carebuddy.ui.home.NurseHomeActivity

class NurseLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNurseLoginBinding
    private val viewModel by lazy { NurseLoginViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNurseLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val firebaseToken = preferences.getString("firebase_token", null)

        binding.btNurseLogin.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val password  = binding.etPassword.text.toString()
            viewModel.loginClickListener(email, password, firebaseToken!!)
        }

        binding.etEmail.addTextChangedListener {

            Log.w("NurseLoginActivity", "onCreate: " )
        }

        binding.etEmail.doAfterTextChanged { hideErrorLogin() }
        binding.etPassword.doAfterTextChanged { hideErrorLogin() }


        viewModel.callActivity.observe(this) {

            Log.d("NurseLoginActivity", "onCreate: callActivity observer called")

            it?.let {
                if (it){
                    val intent = Intent(this, NurseHomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        viewModel.loginErrorMessage.observe(this){

            it?.let {

                if (it.isNotEmpty())
                    showErrorLogin(it)
            }
        }

        viewModel.showNetworkError.observe(this){

            Log.d("NurseLoginActivity", "onCreate: showNetwork observer called")

            it?.let {

                if (it)
                    showNetworkError()
            }
        }
    }

    private fun showErrorLogin(s: String) {

        binding.cvLoginError.visibility = View.VISIBLE
        binding.tvLoginError.text = s
    }

    private fun hideErrorLogin() {
        binding.cvLoginError.visibility = View.INVISIBLE
    }

    private fun showNetworkError() {
        Toast.makeText(this, "Something went wrong! Please try again later", Toast.LENGTH_SHORT).show()
    }

}