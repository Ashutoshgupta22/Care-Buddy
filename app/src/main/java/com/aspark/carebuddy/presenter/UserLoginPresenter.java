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
import com.aspark.carebuddy.view.MapActivity;
import com.aspark.carebuddy.view.user.UserHomeActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginPresenter implements Contract.Presenter.PresenterUserLogin {

    Context context;
    Contract.View.UserLoginView loginView;
    Contract.View destroyView;

    public UserLoginPresenter(Contract.View.UserLoginView loginView) {
        this.loginView = loginView;
    }

    public UserLoginPresenter() {
    }

    public UserLoginPresenter(Contract.View destroyView) {
        this.destroyView = destroyView;
    }

    @Override
    public void userLoginClickListener(Context context, String sEmail, String sPassword) {

        loginView.hideUserLoginError();
        this.context = context;

        callLoginApi(sEmail,sPassword);

        //TODO add try and catch to get socketTimeoutException

//        try {
//
//
//        } catch (SocketTimeoutException exception) {
//
//            loginView.showUserLoginError("Something went wrong!");
//        }

    }

    @Override
    public void userSignedInLogin(Context context) {

        // get signed in user's all data from server
        getUserData(UserModel.getCurrentUser().getEmail());

        Intent intent = new Intent(context,UserHomeActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    private void getUserData(String email) {

        Log.i("UserLoginPresenter", "getUserData: Getting user data from server");

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.getUserData(email)
                .enqueue(new Callback<Map<String, Object>>() {
                    @Override
                    public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            Map<String, Object> userDataMap = response.body();

                            UserModel.getCurrentUser().setName((String) userDataMap.get("name"));
                            UserModel.getCurrentUser().setEmail((String) userDataMap.get("email"));
                            UserModel.getCurrentUser().setLatitude((double) userDataMap.get("latitude"));
                            UserModel.getCurrentUser().setLongitude((double) userDataMap.get("longitude"));
                        } else
                            Log.e("UserLoginPresenter", "onResponse: getUserData response Unsuccessful");
                    }

                    @Override
                    public void onFailure(Call<Map<String, Object>> call, Throwable t) {

                        Log.e("UserLoginPresenter", "onFailure: getUserData FAILED, " + t.getMessage());
                    }
                });
    }

    private void callLoginApi(String sEmail, String sPassword) {

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        UserModel user = UserModel.getCurrentUser();
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

                           // Intent intent = new Intent(context.getApplicationContext(), MapActivity.class);

                            getUserData(user.getEmail());
                            Intent intent = new Intent(context.getApplicationContext(), UserHomeActivity.class);
                            //destroys all previous tasks
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);
                            ((Activity) context).finish();

                           // destroyView.destroyActivity();

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

                Log.e("UserLoginPresenter", "onFailure: User Login Failed", t);



            }
        });
    }
}
