package com.aspark.carebuddy.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aspark.carebuddy.api.UserApi
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.model.User.Companion.currentUser
import com.aspark.carebuddy.retrofit.RetrofitService
import com.aspark.carebuddy.retrofit.request.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserLoginViewModel: ViewModel() {

    private val mCallActivity = MutableLiveData<Boolean>()
    val callActivity: LiveData<Boolean>
        get() = mCallActivity
    private val mLoginErrorMessage = MutableLiveData<String>()
    val loginErrorMessage: LiveData<String>
        get() = mLoginErrorMessage


    fun userLoginClickListener(email: String, password: String) {

        callLoginApi(LoginRequest(email,password))

        //TODO add try and catch to get socketTimeoutException

//        try {
//
//
//        } catch (SocketTimeoutException exception) {
//
//            loginView.showUserLoginError("Something went wrong!");
//        }

    }

    private fun callLoginApi(loginRequest: LoginRequest){

        val userApi = RetrofitService().retrofit.create(UserApi::class.java)

        userApi.loginUser(loginRequest)
            .enqueue(object : Callback<User?> {

                override fun onResponse(call: Call<User?>, response: Response<User?>) {

                        if (response.isSuccessful ) {


                             if (response.body() != null) {

                                 Log.i("UserLoginViewModel", "Welcome Back!")
                                 currentUser = response.body()!!
                                 Log.i("UserLoginViewModel", "onResponse: current user name ${currentUser.name}")
                                 mCallActivity.value = true
                             }
                            else {
                                 Log.i(
                                     "UserLoginViewModel",
                                     "onResponse: Error response message= returned user from " +
                                             "backend is null")

                                 mLoginErrorMessage.value = "Invalid email or password"
                             }
                        }
                        else Log.e("UserLoginViewModel", "onResponse: Response unsuccessful ")
                }

                override fun onFailure(call: Call<User?>, t: Throwable) {
                    Log.e("UserLoginViewModel", "onFailure: User Login Failed", t)
                }
            })
    }

//   fun getUserData(email: String){
//
//        Log.i("UserLoginPresenter", "getUserData: Getting user data from server")
//        val retrofitService = RetrofitService()
//        val userApi = retrofitService.retrofit.create(UserApi::class.java)
//        userApi.getUserData(email!!)
//            .enqueue(object : Callback<Map<String?, Any?>?> {
//                override fun onResponse(
//                    call: Call<Map<String?, Any?>?>,
//                    response: Response<Map<String?, Any?>?>
//                ) {
//                    if (response.isSuccessful && response.body() != null) {
//                        val userDataMap = response.body()
//                        currentUser.name = userDataMap!!["name"] as String?
//                        currentUser.email = userDataMap["email"] as String?
//                        currentUser.latitude = userDataMap["latitude"] as Double
//                        currentUser.longitude = userDataMap["longitude"] as Double
//                    } else Log.e(
//                        "UserLoginPresenter",
//                        "onResponse: getUserData response Unsuccessful"
//                    )
//                }
//
//                override fun onFailure(call: Call<Map<String?, Any?>?>, t: Throwable) {
//                    Log.e("UserLoginPresenter", "onFailure: getUserData FAILED, " + t.message)
//                }
//            })
//
//    }

//    fun getSignedInUserData(){
//
//        // get signed in user's all data from server
//        getUserData(currentUser.email)
//        val intent = Intent(context, UserHomeActivity::class.java)
//        context.startActivity(intent)
//        (context as Activity).finish()
//    }
}