package com.aspark.carebuddy.api

import com.aspark.carebuddy.model.Nurse
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.retrofit.request.BookServiceRequest
import com.aspark.carebuddy.retrofit.request.LocationData
import com.aspark.carebuddy.retrofit.request.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @POST("/api/user/registration")
    fun registerUser(@Body user: User): Call<User>

    @POST("/api/user/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<User?>

    @POST("/api/user/save-location")
    fun saveLocation(@Body locationData: LocationData): Call<Boolean>

    @GET("/api/user/get-user/{email}")
    fun getUserData(@Path(value = "email") email: String): Call<Map<String, Any>>

    @POST("/api/user/book-service")
    fun bookService(@Body request: BookServiceRequest): Call<Nurse>
}