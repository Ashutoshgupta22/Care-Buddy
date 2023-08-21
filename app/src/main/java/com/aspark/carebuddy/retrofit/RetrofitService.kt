package com.aspark.carebuddy.retrofit

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService private constructor(){

    companion object {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.20:8080") //ip address and server port number
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }
}