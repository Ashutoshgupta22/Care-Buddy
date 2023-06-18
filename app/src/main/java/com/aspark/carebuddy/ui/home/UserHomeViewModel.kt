package com.aspark.carebuddy.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.aspark.carebuddy.api.UserApi
import com.aspark.carebuddy.model.Nurse
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.retrofit.RetrofitService
import com.aspark.carebuddy.retrofit.request.BookServiceRequest
import com.aspark.carebuddy.retrofit.request.LocationData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserHomeViewModel: ViewModel() {

    fun bookServiceClickListener(){

        bookService()
    }


    private fun bookService() {

        val retrofitService = RetrofitService()
        val userApi = retrofitService.retrofit.create(UserApi::class.java)

        //TODO why is location data needed
        val locationData = LocationData()
        locationData.latitude = User.currentUser.latitude
        locationData.longitude = User.currentUser.longitude

        Log.d("UserHomeViewModel", "bookService: Calling backend for  " +
                (User.currentUser.email)
        )

        val userEmail = User.currentUser.email
        val request = BookServiceRequest(userEmail!!)


        userApi.bookService(request)
            .enqueue(object : Callback<Nurse?> {

                override fun onResponse(call: Call<Nurse?>, response: Response<Nurse?>) {

                    if (response.isSuccessful && response.body() != null) {

                        Log.d("UserHomeViewModel", "onResponse: book service success")
                    } else
                        Log.e("UserHomeViewModel", "onResponse: Book service Unsuccessful: "
                                +response.body())
                }

                override fun onFailure(call: Call<Nurse?>, t: Throwable) {

                    Log.e("UserHomeViewModel", "onFailure: Book service Failed",t.cause )
                }
            })
    }


}