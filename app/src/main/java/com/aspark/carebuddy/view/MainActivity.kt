package com.aspark.carebuddy.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.R;
import com.aspark.carebuddy.model.UserModel;
import com.aspark.carebuddy.presenter.LoginPresenter;
import com.aspark.carebuddy.presenter.UserLoginPresenter;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity implements Contract.View{

    MaterialCardView cardView_nurse, cardView_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(getPackageName(),MODE_PRIVATE);
        boolean isSignedIn = preferences.getBoolean("isSignedIn", false);
        String username = preferences.getString("username",null);

        //TODO to get all the user data send username to backend and get user object in return
        Log.d("MainActivity", "onCreate: currentUser: "+username);
        Log.i("MainActivity", "check isSignedIn "+isSignedIn);

        if (isSignedIn) {

            UserModel.getCurrentUser().setEmail(username);
            Contract.Presenter.PresenterUserLogin presenterUserLogin = new UserLoginPresenter();
            presenterUserLogin.userSignedInLogin(this);
        }

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

    @Override
    public void destroyActivity() {

        finish();
        Log.i("MainActivity", "destroyActivity: Activity Destroyed!");
    }
}