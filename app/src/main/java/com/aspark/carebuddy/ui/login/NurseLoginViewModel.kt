package com.aspark.carebuddy.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aspark.carebuddy.api.NurseApi
import com.aspark.carebuddy.model.Nurse
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.retrofit.RetrofitService
import com.aspark.carebuddy.retrofit.request.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NurseLoginViewModel : ViewModel() {

    private val mCallActivity = MutableLiveData<Boolean>()
    val callActivity: LiveData<Boolean>
        get() = mCallActivity
    private val mLoginErrorMessage = MutableLiveData<String>()
    val loginErrorMessage: LiveData<String>
        get() = mLoginErrorMessage
    var showNetworkError = MutableLiveData<Boolean>()

    fun loginClickListener(email: String, password: String) {

        val nurseApi = RetrofitService()
            .retrofit
            .create(NurseApi::class.java)

        val loginRequest = LoginRequest(email,password)
        Log.d("NurseLoginViewModel", "loginClickListener: $loginRequest")

        //TODO catch java.net.ConnectException, when couldn't connect with backend and show toast
        nurseApi.loginNurse(loginRequest)
            .enqueue(object : Callback<Nurse>{

                override fun onResponse(
                    call: Call<Nurse>, response: Response<Nurse>) {

                    if (response.isSuccessful ) {

                        Log.i("NurseLoginViewModel", "Welcome Back!")
                        Nurse.currentNurse = response.body()!!

                        Log.i("NurseLoginViewModel", "onResponse: " +
                                "current user name ${User.currentUser.name}")
                        mCallActivity.value = true
                    }
                    else if(response.code() == 403){
                        Log.e("NurseLoginViewModel", "onResponse: Response " +
                                "unsuccessful invalid email ")

                        mLoginErrorMessage.value = "Invalid email or password"
                    }
                    else if (response.code() == 401){

                        Log.e("NurseLoginViewModel", "onResponse: Response " +
                                "email not registered ")

                        mLoginErrorMessage.value = "Email not registered"

                    }
                }

                override fun onFailure(call: Call<Nurse>, t: Throwable) {
                    showNetworkError.value = true
                    Log.e("NurseLoginViewModel", "onFailure: Nurse login Failed", t.cause )
                }
            })
    }
}