package com.aspark.carebuddy.repository

import android.util.Log
import com.aspark.carebuddy.api.UserApi
import com.aspark.carebuddy.model.Nurse
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.retrofit.HttpStatusCode
import com.aspark.carebuddy.retrofit.request.BookServiceRequest
import com.aspark.carebuddy.retrofit.request.LocationData
import com.aspark.carebuddy.retrofit.request.LoginRequest
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor( private val userApi: UserApi) {


    fun bookService() {

         val userEmail = User.currentUser.email
         val request = BookServiceRequest(userEmail!!)

         Log.d("Repository", "bookService: " +
                 "Calling backend for  $userEmail")

        userApi.bookService(request)
            .enqueue(object : Callback<Nurse?> {

                override fun onResponse(call: Call<Nurse?>, response: Response<Nurse?>) {

                    if (response.isSuccessful && response.body() != null) {

                        Log.d("Repository", "onResponse: book service success")
                    } else
                        Log.e("Repository", "onResponse: Book service Unsuccessful: "
                                +response.body())
                }

                override fun onFailure(call: Call<Nurse?>, t: Throwable) {

                    Log.e("Repository", "onFailure: Book service Failed",t )
                }
            })
    }

    fun signUp(user: User, callback: (HttpStatusCode) -> Unit) {

        userApi.signUp(user)
            .enqueue(object : Callback<Boolean> {

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {

                    if (response.isSuccessful ) {

                        Log.i("Repository", "onResponse: " +
                                "User registered successfully")

                        //mStartActivity.value = true
                        callback(HttpStatusCode.OK)

                    } else {

//                        try {
//                            assert(response.errorBody() != null)
//                            val errorBody = response.errorBody()!!.string()
//                            val jsonObject = JSONObject(errorBody.trim { it <= ' ' })
//                            val errorMessage = jsonObject.getString("message")
//                            Log.w("TAG", "onResponse: Response Code=" + response.code())
//                            Log.w("TAG", "onResponse: Response Msg=$errorMessage")
//
//                            if (response.code() == 403) {
//                                //TODO show below toast
//                                // Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
//                            }
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                        } catch (e: JSONException) {
//                            e.printStackTrace()
//                        }
                        if ( response.code() == HttpStatusCode.FORBIDDEN.code)
                            Log.e("Repository", "onResponse: FORBIDDEN ")


                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {

                    //mSignUpFailedError.value = t
                    callback(HttpStatusCode.FAILED)

                    Log.e("Repository", "onFailure: SignUp Failed", t)
                }
            })
    }

    fun login(loginRequest: LoginRequest, firebaseToken: String,
                             callback: (HttpStatusCode) -> Unit) {

        userApi.loginUser(loginRequest)
            .enqueue(object : Callback<User?> {

                override fun onResponse(call: Call<User?>, response: Response<User?>) {

                    if (response.isSuccessful && response.body() != null) {

                        Log.i("Repository", "Welcome Back!")
                        User.currentUser = response.body()!!

                        Log.i("Repository", "onResponse: " +
                                "current user name ${User.currentUser.name}")

                        //mCallActivity.value = true
                        callback(HttpStatusCode.OK)

                        setFirebaseToken(loginRequest.email, firebaseToken)
                    }

                    else if (response.code() == HttpStatusCode.UNAUTHORIZED.code){

                        Log.e("Repository", "onResponse: Response " +
                                "email not registered ")

                       // mLoginErrorMessage.value = "Email not registered"
                        callback(HttpStatusCode.UNAUTHORIZED)
                    }

                    else if(response.code() == HttpStatusCode.FORBIDDEN.code){
                        Log.e("Repository", "onResponse: Response " +
                                "unsuccessful invalid email ")

                        // mLoginErrorMessage.value = "Invalid email or password"
                        callback(HttpStatusCode.FORBIDDEN)
                    }
                }

                override fun onFailure(call: Call<User?>, t: Throwable) {
                    Log.e("Repository", "onFailure: User Login Failed", t)
                }
            })
    }

    private fun setFirebaseToken(email: String, firebaseToken: String) {

        User.currentUser.firebaseToken = firebaseToken

        userApi
            .setUserFirebaseToken(email, firebaseToken)
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {

                    if (response.isSuccessful && response.body() == true)
                        Log.i("Repository", "onResponse: setFirebaseToken" +
                                " successful")
                    else
                        Log.e("Repository", "onResponse: setFirebaseToken " +
                                "response unsuccessful" )
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.e("Repository", "onFailure: setFirebaseToken Failed", t )
                }
            })
    }

    fun saveLocation(locationData: LocationData, callback: (HttpStatusCode) -> Unit) {

        userApi.saveLocation(locationData)
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {

                    //TODO showing response successful when currentUser is null.
                    if (response.isSuccessful && response.body() == true) {

                        Log.i("Repository", "onResponse: saveLocation Successful")
                        //mStartActivity.value = true
                        callback( HttpStatusCode.OK )

                    } else {
                        Log.e("Repository",
                            "onResponse: saveLocation Response FAILED code= " +
                                    response.code() + response.message())
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.e("Repository", "onFailure: saveLocation FAILED: $t")
                }
            })
    }


}