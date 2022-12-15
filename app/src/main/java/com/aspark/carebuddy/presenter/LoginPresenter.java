package com.aspark.carebuddy.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.view.nurse.NurseHomeActivity;
import com.aspark.carebuddy.view.nurse.NurseLoginActivity;
import com.aspark.carebuddy.view.user.SignUpUserActivity;
import com.aspark.carebuddy.view.user.UserLoginActivity;

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
    public void signUpBtnClickListener(Context context) {

        Intent intent = new Intent(context.getApplicationContext(), SignUpUserActivity.class);
        context.startActivity(intent);

    }

    @Override
    public void loginNurseClickListener(Context context) {

        Intent intent = new Intent(context.getApplicationContext(), NurseHomeActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
}
