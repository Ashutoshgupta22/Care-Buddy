package com.aspark.carebuddy.api

import com.aspark.carebuddy.model.Nurse
import com.aspark.carebuddy.retrofit.request.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface NurseApi {

    @POST("/api/nurse/login")
    fun loginNurse(@Body loginRequest: LoginRequest) : Call<Nurse>

    @POST("/api/nurse/set-firebase-token/{email}")
    fun setNurseFirebaseToken( @Path(value = "email") email: String,
                               @Body firebaseToken: String) : Call<Boolean>
}