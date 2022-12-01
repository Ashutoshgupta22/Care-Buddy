package com.aspark.carebuddy.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.R;
import com.aspark.carebuddy.presenter.LoginPresenter;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    MaterialCardView cardView_nurse, cardView_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView_nurse = findViewById(R.id.nurseCard);
        cardView_user = findViewById(R.id.userCard);

        cardView_nurse.setOnClickListener(view -> {

            Contract.Presenter presenter = new LoginPresenter();
            presenter.nurseCardClickListener(this);
        });

        cardView_user.setOnClickListener(view -> {

            Contract.Presenter presenter = new LoginPresenter();
            presenter.userCardClickListener(this);
        });
    }
}