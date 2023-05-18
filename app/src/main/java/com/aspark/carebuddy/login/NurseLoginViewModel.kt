package com.aspark.carebuddy.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aspark.carebuddy.api.NurseApi
import com.aspark.carebuddy.model.Nurse
import com.aspark.carebuddy.retrofit.RetrofitService
import com.aspark.carebuddy.retrofit.request.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NurseLoginViewModel : ViewModel() {

    var callActivity = MutableLiveData<Boolean>()
    var isErrorVisible = MutableLiveData<Boolean>()
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
                    call: Call<Nurse>,
                    response: Response<Nurse>) {

                    if (response.isSuccessful){

                        Nurse.currentNurse = response.body()!!

                        Log.i("NurseLoginViewModel",
                            "onResponse: nurse logged in: ${Nurse.currentNurse}")

                        callActivity.value = true
                      //  isErrorVisible.value = false

                    }
                    else {

                        isErrorVisible.value = true
                        Log.e(
                            "NurseLoginViewModel",
                            "onResponse: Login nurse response unsuccessful: " +
                                    "Invalid email or password")
                    }
                }

                override fun onFailure(call: Call<Nurse>, t: Throwable) {
                    showNetworkError.value = true
                    Log.e("NurseLoginViewModel", "onFailure: Nurse login Failed", t.cause )
                }
            })
    }
}