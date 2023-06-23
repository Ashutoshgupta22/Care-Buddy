package com.aspark.carebuddy.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspark.carebuddy.repository.Repository
import com.aspark.carebuddy.retrofit.HttpStatusCode
import com.aspark.carebuddy.retrofit.request.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor( private val repo: Repository ) : ViewModel() {

    private val mCallActivity = MutableLiveData<Boolean>()
    val callActivity: LiveData<Boolean> = mCallActivity
    private val mLoginErrorMessage = MutableLiveData<String>()
    val loginErrorMessage: LiveData<String> = mLoginErrorMessage


    fun userLoginClickListener(email: String, password: String, firebaseToken: String) {

        val loginRequest = LoginRequest( email, password)

        viewModelScope.launch( Dispatchers.IO ) {

            repo.login( loginRequest, firebaseToken ) {

                when(it) {

                    HttpStatusCode.OK -> mCallActivity.postValue(true)

                    HttpStatusCode.FAILED -> TODO()

                    HttpStatusCode.UNAUTHORIZED ->
                        mLoginErrorMessage.postValue( "Email not registered")

                    HttpStatusCode.FORBIDDEN ->
                        mLoginErrorMessage.postValue( "Invalid email or password" )
                }
            }
        }

    //TODO add try and catch to get socketTimeoutException

//        try {
//
//
//        } catch (SocketTimeoutException exception) {
//
//            loginView.showUserLoginError("Something went wrong!");
//        }

    }
}