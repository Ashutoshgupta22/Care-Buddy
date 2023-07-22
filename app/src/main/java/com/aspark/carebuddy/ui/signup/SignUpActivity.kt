package com.aspark.carebuddy.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aspark.carebuddy.databinding.ActivitySignUpBinding
import com.aspark.carebuddy.ui.login.LoginActivity
import com.aspark.carebuddy.model.User.Companion.currentUser
import dagger.hilt.android.AndroidEntryPoint
import java.util.logging.Level
import java.util.logging.Logger

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    private var sFirstName: String? = null
    var sAge: String? = null
    var sEmail: String? = null
    var sPassword: String? = null
    var sConfirmPassword: String? = null
    var validInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.startActivity.observe(this){
            it?.let {

                if (it) {
                    val intent =
                        Intent(this, LoginActivity::class.java)
                    intent.putExtra("emailSent", "emailSent")
                    startActivity(intent)
                    finish()
                }
            }
        }

        viewModel.signUpFailedError.observe(this){

            it?.let { showSignUpFailed(it) }
        }


        binding.userSignUpBtn.setOnClickListener {

            sFirstName = binding.userSignUpName.text.toString().trim()
            sAge = binding.userSignUpAge.text.toString().trim()
            sEmail = binding.userSignUpEmail.text.toString().trim()
            sPassword = binding.userSignUpPassword.text.toString().trim()
            sConfirmPassword = binding.userSignUpConfirmPassword.text.toString().trim()

            validInput = true

            if (verifyInput(sFirstName!!, sAge!!, sEmail!!, sPassword!!, sConfirmPassword!!)) {

                val user = currentUser
                user.firstName = sFirstName
                user.age = sAge!!.toInt()
                user.email = sEmail
                user.password = sPassword

                viewModel.signUpClickListener(user)
            }
        }
    }

    private fun showSignUpFailed( s: String) {

        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
        Logger.getLogger(SignUpActivity::class.java.name).log(Level.SEVERE,
            "Error Occurred")
    }

    private fun verifyInput(
        sFirstName: String, sAge: String, sEmail: String,
        sPassword: String, sConfirmPassword: String): Boolean {

        if (sFirstName == "") {
            binding.userSignUpName.error = "Name can not be empty"
            validInput = false
        }
        if (sAge == "") {
            binding.userSignUpAge.error = "Age can not be empty"
            validInput = false
        }
        if (sEmail == "") {
            binding.userSignUpEmail.error = "Email can not be empty"
            validInput = false
        }
        if (sPassword == "") {
            binding.userSignUpPassword.error = "Password can not be empty"
            validInput = false
        } else {
            if (sPassword.length < 6) {
                binding.userSignUpPassword.error = "Password should contain at least 6 characters "
                validInput = false
            } else {
                if (sPassword != sConfirmPassword) {
                    binding.userSignUpConfirmPassword.error = "Confirm Password did not match"
                    validInput = false
                }
            }
        }
        return validInput
    }
}