package com.aspark.carebuddy.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.view.user.UserLoginActivity;

public class SignUpPresenter implements Contract.Presenter.PresenterSignUp {


    @Override
    public void signUpClickListener(Context context) {

        Log.i("TAG", "signUpClickListener: called");
        Intent intent = new Intent(context.getApplicationContext(), UserLoginActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();

    }
}
