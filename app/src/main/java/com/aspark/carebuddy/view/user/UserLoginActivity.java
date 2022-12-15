package com.aspark.carebuddy.view.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.R;
import com.aspark.carebuddy.presenter.LoginPresenter;
import com.aspark.carebuddy.presenter.UserLoginPresenter;

public class UserLoginActivity extends AppCompatActivity implements Contract.View.UserLoginView {

    EditText emailEditText,passwordEditText;
    Button loginBtn;
    TextView signUpTextView,loginErrorTextView;
    String sEmail,sPassword;
    CardView loginErrorCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        emailEditText = findViewById(R.id.userLoginEmail);
        passwordEditText = findViewById(R.id.userLoginPassword);
        loginBtn = findViewById(R.id.userLoginBtn);
        signUpTextView = findViewById(R.id.signUpTextView);
        loginErrorCard = findViewById(R.id.loginErrorCardView);
        loginErrorTextView = findViewById(R.id.loginErrorTextView);

        hideUserLoginError();

        if (getIntent().getStringExtra("emailSent") !=null) {
            loginErrorTextView.setTextColor(Color.GRAY);
            loginErrorTextView.setText("Verification email sent successfully");
            loginErrorCard.setVisibility(View.VISIBLE);
           // showUserLoginError("Verification email sent successfully");

        }

        loginBtn.setOnClickListener(view -> {

            sEmail = emailEditText.getText().toString();
            sPassword = passwordEditText.getText().toString();

            Contract.Presenter.PresenterUserLogin presenter = new UserLoginPresenter(this);
            presenter.userLoginClickListener(this,sEmail,sPassword);

        });

        signUpTextView.setOnClickListener(view -> {
            Contract.Presenter presenter = new LoginPresenter();
            presenter.signUpBtnClickListener(this);
        });

    }

    @Override
    public void showUserLoginError(String message) {

        loginErrorTextView.setTextColor(getResources().getColor(R.color.error,getTheme()));
        loginErrorTextView.setText(message);
        loginErrorCard.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideUserLoginError() {

        loginErrorCard.setVisibility(View.INVISIBLE);

    }
}