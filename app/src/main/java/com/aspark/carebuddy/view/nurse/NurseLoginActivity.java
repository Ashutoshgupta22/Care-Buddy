package com.aspark.carebuddy.view.nurse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.R;
import com.aspark.carebuddy.presenter.LoginPresenter;

public class NurseLoginActivity extends AppCompatActivity {

    EditText etEmail,etPassword;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_login);

        etEmail = findViewById(R.id.nurseLoginEmail);
        etPassword = findViewById(R.id.nurseLoginPassword);
        loginBtn = findViewById(R.id.nurseLoginBtn);

        loginBtn.setOnClickListener(view ->  {

            Contract.Presenter presenter = new LoginPresenter();
            presenter.loginNurseClickListener(this);
        });
    }
}