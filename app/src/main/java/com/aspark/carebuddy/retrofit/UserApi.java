package com.aspark.carebuddy.retrofit;

import com.aspark.carebuddy.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("/api/user/registration")
    Call<UserModel> registerUser(@Body UserModel user);

//    @POST("/api/user/login")
//    Call<UserModel> loginUser(@Body UserModel user);

    @POST("/api/user/login")
    Call<UserLoginResponse> loginUser(@Body UserModel userModel);


}
