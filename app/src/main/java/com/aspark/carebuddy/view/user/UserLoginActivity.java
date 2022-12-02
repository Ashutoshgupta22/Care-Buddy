package com.aspark.carebuddy.view.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.R;
import com.aspark.carebuddy.presenter.LoginPresenter;

public class UserLoginActivity extends AppCompatActivity {

    EditText emailEditText,passwordEditText;
    Button loginBtn;
    TextView signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        emailEditText = findViewById(R.id.userLoginEmail);
        passwordEditText = findViewById(R.id.userLoginPassword);
        loginBtn = findViewById(R.id.userLoginBtn);
        signUpTextView = findViewById(R.id.signUpTextView);

        loginBtn.setOnClickListener(view -> {
            Contract.Presenter presenter = new LoginPresenter();
            presenter.loginBtnUserClickListener(this);

        });

        signUpTextView.setOnClickListener(view -> {
            Contract.Presenter presenter = new LoginPresenter();
            presenter.signUpClickListener(this);
        });

    }
}