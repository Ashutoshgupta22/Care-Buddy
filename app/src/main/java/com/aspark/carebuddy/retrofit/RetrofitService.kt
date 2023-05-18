package com.aspark.carebuddy.retrofit

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    lateinit var retrofit: Retrofit
        private set

    init {
        initializeRetrofit()
    }

    private fun initializeRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.11:8080") //ip address and server port number
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }
}