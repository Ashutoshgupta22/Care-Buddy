package com.aspark.carebuddy.retrofit

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService private constructor(){

    companion object {
        private const val BASE_URL = "http://aspark-care-buddy.ap-south-1.elasticbeanstalk.com"
//        private val BASE_URL = "http://192.168.1.2:8080"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) //ip address and server port number
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }
}