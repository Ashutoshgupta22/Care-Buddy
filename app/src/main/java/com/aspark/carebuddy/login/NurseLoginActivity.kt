package com.aspark.carebuddy.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.aspark.carebuddy.R
import com.aspark.carebuddy.databinding.ActivityNurseLoginBinding
import com.aspark.carebuddy.view.nurse.NurseHomeActivity

class NurseLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNurseLoginBinding
    private val viewModel by lazy { NurseLoginViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNurseLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        viewModel.isErrorVisible.observe(this){

            it?.let {

                if (it)
                    showErrorLogin()
                else
                    hideErrorLogin()
            }
        }

        viewModel.showNetworkError.observe(this){

            Log.d("NurseLoginActivity", "onCreate: showNetwork observer called")

            it?.let {

                if (it)
                    showNetworkError()
            }
        }

        binding.btNurseLogin.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val password  = binding.etPassword.text.toString()
            viewModel.loginClickListener(email,password)
        }

        binding.etEmail.addTextChangedListener {

            Log.w("NurseLoginActivity", "onCreate: " )
        }
    }

    private fun showErrorLogin() {

        binding.cvLoginError.visibility = View.VISIBLE
        binding.tvLoginError.text = getString(R.string.invalid_email_or_password)
    }

    private fun hideErrorLogin() {
        binding.cvLoginError.visibility = View.INVISIBLE
    }

    private fun showNetworkError() {
        Toast.makeText(this, "Something went wrong! Please try again later", Toast.LENGTH_SHORT).show()
    }

}