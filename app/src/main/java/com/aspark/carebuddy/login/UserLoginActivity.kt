package com.aspark.carebuddy.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.aspark.carebuddy.R
import com.aspark.carebuddy.databinding.ActivityUserLoginBinding
import com.aspark.carebuddy.signup.SignUpUserActivity
import com.aspark.carebuddy.view.user.UserHomeActivity

class UserLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserLoginBinding
    private val viewModel: UserLoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //hideUserLoginError()

        if (intent.getStringExtra("emailSent") != null) {

            binding.tvLoginError.setTextColor(Color.GRAY)
            binding.tvLoginError.setText(R.string.email_verified_success)
            binding.cvLoginError.visibility = View.VISIBLE
            // showUserLoginError("Verification email sent successfully");
        }

        viewModel.callActivity.observe(this) {

            val intent =
                Intent(this, UserHomeActivity::class.java)
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

           val sEmail = binding.userLoginEmail.text.toString()
           val sPassword = binding.userLoginPassword.text.toString()

            viewModel.userLoginClickListener(sEmail,sPassword)
        }

        binding.signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpUserActivity::class.java)
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