package com.aspark.carebuddy.retrofit;

import com.aspark.carebuddy.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {

    @POST("/user/save")
    Call<UserModel> saveUser(@Body UserModel user);

}
