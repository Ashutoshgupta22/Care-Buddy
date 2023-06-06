package com.aspark.carebuddy.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aspark.carebuddy.databinding.ActivitySignUpUserBinding
import com.aspark.carebuddy.login.UserLoginActivity
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.model.User.Companion.currentUser
import java.util.logging.Level
import java.util.logging.Logger

class SignUpUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpUserBinding
    private val viewModel: SignUpUserViewModel by viewModels()

    private var sName: String? = null
    var sAge: String? = null
    var sEmail: String? = null
    var sPassword: String? = null
    var sConfirmPassword: String? = null
    var validInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.startActivity.observe(this){
            it?.let {

                if (it) {
                    val intent =
                        Intent(this, UserLoginActivity::class.java)
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

            sName = binding.userSignUpName.text.toString().trim()
            sAge = binding.userSignUpAge.text.toString().trim()
            sEmail = binding.userSignUpEmail.text.toString().trim()
            sPassword = binding.userSignUpPassword.text.toString().trim()
            sConfirmPassword = binding.userSignUpConfirmPassword.text.toString().trim()

            validInput = true

            if (verifyInput(sName!!, sAge!!, sEmail!!, sPassword!!, sConfirmPassword!!)) {

                val user = currentUser
                user.name = sName
                user.age = sAge!!.toInt()
                user.email = sEmail
                user.password = sPassword

                viewModel.signUpClickListener(user)
            }
        }
    }

    private fun showSignUpFailed(t:Throwable) {

        Toast.makeText(this, "Save Failed!!", Toast.LENGTH_LONG).show()
        Logger.getLogger(SignUpUserActivity::class.java.name).log(Level.SEVERE, "Error Occurred", t)
    }

    private fun verifyInput(
        sName: String, sAge: String, sEmail: String,
        sPassword: String, sConfirmPassword: String
    ): Boolean {

        if (sName == "") {
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
                binding.userSignUpPassword.error = "Password should contain atleast 6 characters "
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