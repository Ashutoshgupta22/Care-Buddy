package com.aspark.carebuddy.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.aspark.carebuddy.R
import com.aspark.carebuddy.databinding.ActivityLoginBinding
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.ui.signup.SignUpActivity
import com.aspark.carebuddy.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val isUserSignedIn = preferences.getBoolean("isUserSignedIn", false)

        if (isUserSignedIn) {

            val userEmail = preferences.getString("userEmail", null)
            Log.d("LoginActivity", "onCreate: currentUser: $userEmail")

            User.currentUser.email = userEmail

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()

        }

        if (intent.getStringExtra("emailSent") != null) {

            binding.tvLoginError.setTextColor(Color.GRAY)
            binding.tvLoginError.setText(R.string.email_verified_success)
            binding.cvLoginError.visibility = View.VISIBLE
            // showUserLoginError("Verification email sent successfully");
        }

        viewModel.callActivity.observe(this) {

            val intent =
                Intent(this, HomeActivity::class.java)
            //destroys all previous tasks
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        viewModel.loginErrorMessage.observe(this){

            it?.let {
                showUserLoginError(it)
            }
        }

        binding.userLoginBtn.setOnClickListener {

            val firebaseToken = preferences.getString("firebase_token", null)

            val sEmail = binding.userLoginEmail.text.toString()
            val sPassword = binding.userLoginPassword.text.toString()

            viewModel.userLoginClickListener(sEmail, sPassword, firebaseToken!!)
        }

        binding.signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.userLoginEmail.doAfterTextChanged {
            hideUserLoginError()
        }
        binding.userLoginPassword.doAfterTextChanged {
            hideUserLoginError()
        }

    }

    private fun showUserLoginError(message: String?) {
        binding.tvLoginError.setTextColor(resources.getColor(R.color.error, theme))
        binding.tvLoginError.text = message
        binding.cvLoginError.visibility = View.VISIBLE
    }

     private fun hideUserLoginError() {
        binding.cvLoginError.visibility = View.INVISIBLE
    }
}