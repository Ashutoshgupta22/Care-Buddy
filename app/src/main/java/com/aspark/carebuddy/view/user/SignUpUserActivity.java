package com.aspark.carebuddy.view.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.R;
import com.aspark.carebuddy.presenter.SignUpPresenter;

public class SignUpUserActivity extends AppCompatActivity {

    EditText nameEditText, ageEditText, emailEditText,passwordEditText, confirmPasswordEditText;
    Spinner genderSpinner;
    Button signUpBtn;
    String sName, sAge, sEmail,sPassword, sConfirmPassword;

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


            Contract.Presenter.PresenterSignUp presenter = new SignUpPresenter();
            presenter.signUpClickListener(this);

        });

    }
}