package com.aspark.carebuddy.repository

import android.util.Log
import com.aspark.carebuddy.api.NurseApi
import com.aspark.carebuddy.api.UserApi
import com.aspark.carebuddy.model.Nurse
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.model.User.Companion.currentUser
import com.aspark.carebuddy.retrofit.HttpStatusCode
import com.aspark.carebuddy.retrofit.request.BookServiceRequest
import com.aspark.carebuddy.retrofit.request.LocationData
import com.aspark.carebuddy.retrofit.request.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor( private val userApi: UserApi,
                                      private val nurseApi: NurseApi) {


    fun bookService() {

         val userEmail = currentUser.email
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
                                "current user email ${currentUser.email}")

                        //mCallActivity.value = true
                        callback(HttpStatusCode.OK)

                        setFirebaseToken(currentUser.email!!, firebaseToken)
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

        currentUser.firebaseToken = firebaseToken

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

    fun getUserData(email: String, callback: (HttpStatusCode) -> Unit) {

        userApi
            .getUserData(email)
            .enqueue(object : Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {

                    if(response.isSuccessful && response.body() != null) {

                        currentUser = response.body()!!
                        callback(HttpStatusCode.OK)
                    }
                    else  {
                        Log.e("Repository", "onResponse: getUserData " +
                                "Response unsuccessful" )
                        callback(HttpStatusCode.FAILED)
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {

                    Log.e("Repository", "onFailure: getUserdata failed", t)
                    callback(HttpStatusCode.FAILED)
                }
            })
    }

    fun getTopNurses(pincode: String, callback: (ArrayList<Nurse>) -> Unit ) {

        userApi
            .getTopNurses(pincode)
            .enqueue(object : Callback<ArrayList<Nurse>> {

                override fun onResponse(
                    call: Call<ArrayList<Nurse>>,
                    response: Response<ArrayList<Nurse>>
                ) {

                    if (response.isSuccessful) {

                        if(response.body() != null)
                            callback(response.body()!!)

                        else {
                            Log.e("Repository", "onResponse: getTopNurses response " +
                                    "body is null")
                            callback(arrayListOf())
                        }
                    }
                    else {
                        Log.e("Repository", "onResponse: getTopNurses response " +
                                "unsuccessful")
                        callback(arrayListOf())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Nurse>>, t: Throwable) {

                    callback(arrayListOf())
                    Log.e("Repository", "onResponse: getTopNurses response unsuccessful ")
                }
            })
    }

    fun getNurseById(id: Int, callback: (HttpStatusCode, Nurse?) -> Unit) {

        nurseApi
            .getNurseById(id)
            .enqueue(object : Callback<Nurse> {
                override fun onResponse(call: Call<Nurse>, response: Response<Nurse>) {

                    if (response.isSuccessful && response.body() != null) {

                        callback(HttpStatusCode.OK, response.body())
                    }
                    else {
                        Log.e("Repository", "onResponse: getNurseById Unsuccessful" )
                        callback(HttpStatusCode.FAILED, null)
                    }
                }

                override fun onFailure(call: Call<Nurse>, t: Throwable) {
                    Log.e("Repository", "onFailure: getNurseById Failed", t )
                    callback(HttpStatusCode.FAILED, null)
                }

            })
    }
}