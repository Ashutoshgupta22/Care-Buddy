package com.aspark.carebuddy.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.model.UserModel;
import com.aspark.carebuddy.retrofit.RetrofitService;
import com.aspark.carebuddy.retrofit.UserApi;
import com.aspark.carebuddy.view.user.SignUpUserActivity;
import com.aspark.carebuddy.view.user.UserLoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPresenter implements Contract.Presenter.PresenterSignUp {

    Context context;


    @Override
    public void signUpClickListener(Context context, UserModel user) {

        Log.i("TAG", "signUpClickListener: called");
        this.context = context;
        callSignUpApi(user);
    }

    private void callSignUpApi(UserModel user) {

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.registerUser(user)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Save successful!", Toast.LENGTH_SHORT).show();

                          //  userLoginView.showUserLoginError("Verification email sent successfully");

                            Intent intent = new Intent(context.getApplicationContext(), UserLoginActivity.class);
                            intent.putExtra("emailSent","emailSent");
                            context.startActivity(intent);
                            ((Activity)context).finish();
                        }
                        else {

                            try {
                                assert response.errorBody() != null;
                                String errorBody = response.errorBody().string();
                                JSONObject jsonObject = new JSONObject(errorBody.trim());
                                String errorMessage = jsonObject.getString("message");

                                Log.w("TAG", "onResponse: Response Code=" +response.code());
                                Log.w("TAG", "onResponse: Response Msg=" +errorMessage);
                                if(response.code()== 403) {
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                                }
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Toast.makeText(context, "Save Failed!!", Toast.LENGTH_LONG).show();
                        Logger.getLogger(SignUpUserActivity.class.getName()).log(Level.SEVERE,"Error Occurred",t);

                    }
                });
    }
}
