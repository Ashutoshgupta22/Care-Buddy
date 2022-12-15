package com.aspark.carebuddy.view.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.R;
import com.aspark.carebuddy.model.UserModel;
import com.aspark.carebuddy.presenter.SignUpPresenter;


public class SignUpUserActivity extends AppCompatActivity {

    EditText nameEditText, ageEditText, emailEditText,passwordEditText, confirmPasswordEditText;
    Spinner genderSpinner;
    Button signUpBtn;
    String sName, sAge, sEmail,sPassword, sConfirmPassword;
    boolean validInput=true;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);

        nameEditText = findViewById(R.id.userSignUpName);
        ageEditText = findViewById(R.id.userSignUpAge);
        emailEditText = findViewById(R.id.userSignUpEmail);
        passwordEditText = findViewById(R.id.userSignUpPassword);
        confirmPasswordEditText = findViewById(R.id.userSignUpConfirmPassword);
        genderSpinner = findViewById(R.id.userSignUpGender);
        signUpBtn =findViewById(R.id.userSignUpBtn);

        signUpBtn.setOnClickListener(view -> {

            sName = nameEditText.getText().toString().trim();
            sAge = ageEditText.getText().toString().trim();
            sEmail = emailEditText.getText().toString().trim();
            sPassword = passwordEditText.getText().toString().trim();
            sConfirmPassword = confirmPasswordEditText.getText().toString().trim();

            validInput = true;
            if (verifyInput(sName,sAge,sEmail,sPassword,sConfirmPassword)) {

                UserModel user = new UserModel();
                user.setName(sName);
                user.setAge(Integer.parseInt(sAge));
                user.setEmail(sEmail);
                user.setPassword(sPassword);

                context = this;
                Contract.Presenter.PresenterSignUp presenterSignUp = new SignUpPresenter();
                presenterSignUp.signUpClickListener(context,user);
            }
        });
    }
    private boolean verifyInput(String sName,String sAge,String sEmail, String sPassword, String sConfirmPassword) {

        if (sName.equals("")) {
            nameEditText.setError("Name can not be empty");
            validInput = false;
        }
        if (sAge.equals("")) {
            ageEditText.setError("Age can not be empty");
            validInput = false;
        }

        if (sEmail.equals("")) {
            emailEditText.setError("Email can not be empty");
            validInput = false;
        }
        if (sPassword.equals("")) {
            passwordEditText.setError("Password can not be empty");
            validInput = false;
        }
        else {

            if (sPassword.length() <6) {
                passwordEditText.setError("Password should contain atleast 6 characters ");
                validInput = false;
            }
                else {

                if (!sPassword.equals(sConfirmPassword)) {
                    confirmPasswordEditText.setError("Confirm Password did not match");
                    validInput = false;
                }
            }
        }

        return validInput;
    }
}