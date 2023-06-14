package com.aspark.carebuddy.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aspark.carebuddy.api.UserApi
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.retrofit.RetrofitService
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SignUpUserViewModel: ViewModel() {

    private val mStartActivity = MutableLiveData<Boolean>()
    val startActivity : LiveData<Boolean>
        get() = mStartActivity

    private val mSignUpFailedError = MutableLiveData<Throwable>()
    val signUpFailedError : LiveData<Throwable>
        get() = mSignUpFailedError

    fun signUpClickListener(user: User) = callSignUpApi(user)

    private fun callSignUpApi(user: User) {

        val userApi = RetrofitService().retrofit.create(UserApi::class.java)

        userApi.registerUser(user)
            .enqueue(object : Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {

                    if (response.isSuccessful) {

                        Log.i("SignUpUserViewModel", "onResponse: " +
                                "User registered successfully")
                        //  userLoginView.showUserLoginError("Verification email sent successfully")

                        mStartActivity.value = true

                    } else {

                        try {
                            assert(response.errorBody() != null)
                            val errorBody = response.errorBody()!!.string()
                            val jsonObject = JSONObject(errorBody.trim { it <= ' ' })
                            val errorMessage = jsonObject.getString("message")
                            Log.w("TAG", "onResponse: Response Code=" + response.code())
                            Log.w("TAG", "onResponse: Response Msg=$errorMessage")
                            if (response.code() == 403) {
                                //TODO show below toast
                               // Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {

                    mSignUpFailedError.value = t
                }
            })
    }


}