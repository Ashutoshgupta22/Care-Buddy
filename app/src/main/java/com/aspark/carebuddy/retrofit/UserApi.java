package com.aspark.carebuddy.retrofit;

import com.aspark.carebuddy.model.UserModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @POST("/api/user/registration")
    Call<UserModel> registerUser(@Body UserModel user);

    @POST("/api/user/login")
    Call<UserLoginResponse> loginUser(@Body UserModel userModel);

    @POST("/api/user/save-location")
    Call<Boolean> saveLocation(@Body LocationData locationData);

    @GET("/api/user/{email}")
    Call<Map<String,Object>> getUserData(@Path(value = "email") String email);

    @GET("/api/user/{email}/book-service")
    Call<UserModel> bookService(@Path(value = "email") String email);

}
