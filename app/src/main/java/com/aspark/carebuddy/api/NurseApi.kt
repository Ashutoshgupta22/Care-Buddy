package com.aspark.carebuddy.api

import com.aspark.carebuddy.model.Nurse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NurseApi {

    @GET("/api/nurse/id/{id}")
    fun getNurseById(@Path (value = "id") id: Int): Call<Nurse>
}