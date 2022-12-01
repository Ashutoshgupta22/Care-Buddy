package com.aspark.carebuddy.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.view.NurseLoginActivity;
import com.aspark.carebuddy.view.SignUpUserActivity;
import com.aspark.carebuddy.view.UserHomeActivity;
import com.aspark.carebuddy.view.UserLoginActivity;

public class LoginPresenter implements Contract.Presenter {



    @Override
    public void nurseCardClickListener(Context context) {

        Intent intent = new Intent(context.getApplicationContext(), NurseLoginActivity.class);
        context.startActivity(intent);
      //  ((Activity)context).finish();
    }

    @Override
    public void userCardClickListener(Context context) {

        Intent intent = new Intent(context.getApplicationContext(), UserLoginActivity.class);
        context.startActivity(intent);
      //  ((Activity)context).finish();
    }

    @Override
    public void loginBtnUserClickListener(Context context) {

        Intent intent = new Intent(context.getApplicationContext(), UserHomeActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    @Override
    public void signUpClickListener(Context context) {

        Intent intent = new Intent(context.getApplicationContext(), SignUpUserActivity.class);
        context.startActivity(intent);

    }
}
