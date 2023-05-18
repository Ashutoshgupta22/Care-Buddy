package com.aspark.carebuddy.api

import com.aspark.carebuddy.model.Nurse
import com.aspark.carebuddy.retrofit.request.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NurseApi {

    @POST("/api/nurse/login")
    fun loginNurse(@Body loginRequest: LoginRequest) : Call<Nurse>
}