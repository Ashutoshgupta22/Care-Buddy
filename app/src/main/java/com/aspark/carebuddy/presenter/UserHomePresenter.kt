package com.aspark.carebuddy.presenter

import android.content.Context
import android.util.Log
import com.aspark.carebuddy.Contract
import com.aspark.carebuddy.model.UserModel
import com.aspark.carebuddy.retrofit.LocationData
import com.aspark.carebuddy.retrofit.RetrofitService
import com.aspark.carebuddy.retrofit.UserApi

class UserHomePresenter : Contract.Presenter.PresenterUserHome{

    override fun bookService(context: Context?) {

        val retrofitService = RetrofitService()
        val userApi = retrofitService.retrofit.create(UserApi::class.java)

        val locationData = LocationData()
        locationData.latitude = UserModel.getCurrentUser().latitude;
        locationData.longitude = UserModel.getCurrentUser().longitude;

        userApi.bookService(UserModel.getCurrentUser().email)
    }


}