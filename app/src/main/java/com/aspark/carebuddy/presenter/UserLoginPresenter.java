package com.aspark.carebuddy.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.model.UserModel;
import com.aspark.carebuddy.retrofit.UserLoginResponse;
import com.aspark.carebuddy.retrofit.RetrofitService;
import com.aspark.carebuddy.retrofit.UserApi;
import com.aspark.carebuddy.view.user.UserHomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginPresenter implements Contract.Presenter.PresenterUserLogin {

    Context context;
    Contract.View.UserLoginView loginView;

    public UserLoginPresenter(Contract.View.UserLoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void userLoginClickListener(Context context, String sEmail, String sPassword) {

        loginView.hideUserLoginError();
        this.context = context;
        callLoginApi(sEmail,sPassword);
    }

    private void callLoginApi(String sEmail, String sPassword) {

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        UserModel user = new UserModel();
        user.setEmail(sEmail);
        user.setPassword(sPassword);

        userApi.loginUser(user)
                .enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {

                if(response.isSuccessful()) {

                        assert response.body() != null;
                        String statusCode = response.body().getStatus();
                        String message = response.body().getMessage();

                        if (statusCode.equals("200 OK")) {

                            Log.i("UserLoginPresenter", "Welcome Back!");

                            Intent intent = new Intent(context.getApplicationContext(), UserHomeActivity.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                        else if(statusCode.equals("401 UNAUTHORIZED")) {

                            Log.i("UserLoginPresenter", "onResponse: Error response message=" + message);
                            Log.i("UserLoginPresenter", "onResponse: Error response code=" + statusCode);
                            loginView.showUserLoginError(message);

                        }
                        else {


                        }
                }
                else
                    Log.e("UserLoginPresenter", "onResponse: Response unsuccessful " );
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {

                Log.e("UserLoginPresenter", "onFailure: User Login Failed",t );
            }
        });
    }
}
