package com.aspark.carebuddy.presenter

import android.content.Context
import android.util.Log
import com.aspark.carebuddy.Contract
import com.aspark.carebuddy.model.NurseModel
import com.aspark.carebuddy.model.UserModel
import com.aspark.carebuddy.retrofit.BookServiceRequest
import com.aspark.carebuddy.retrofit.LocationData
import com.aspark.carebuddy.retrofit.RetrofitService
import com.aspark.carebuddy.retrofit.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserHomePresenter : Contract.Presenter.PresenterUserHome{

    override fun bookService(context: Context?) {

        val retrofitService = RetrofitService()
        val userApi = retrofitService.retrofit.create(UserApi::class.java)

        //TODO why is location data needed
        val locationData = LocationData()
        locationData.latitude = UserModel.getCurrentUser().latitude
        locationData.longitude = UserModel.getCurrentUser().longitude

        Log.d("UserHomePresenter", "bookService: Calling backend for  ${UserModel.getCurrentUser().email}")

        val userEmail = UserModel.getCurrentUser().email
        val request = BookServiceRequest(userEmail)

        userApi.bookService(request)
            .enqueue(object : Callback<NurseModel>{

                override fun onResponse(call: Call<NurseModel>, response: Response<NurseModel>) {

                    if (response.isSuccessful) {

                        val selectedNurse = response.body()
                        Log.d("UserHomePresenter", "onResponse: ${selectedNurse?.name}")
                    }
                    else
                        Log.i("UserHomePresenter", "onResponse: Response Unsuccessful: "+response.body())
                }

                override fun onFailure(call: Call<NurseModel>, t: Throwable) {

                    Log.e("UserHomePresenter", "onFailure: ",t.cause )
                }


            })
    }


}