package com.aspark.carebuddy.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.repository.Repository
import com.aspark.carebuddy.retrofit.HttpStatusCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor( private val repo: Repository) : ViewModel() {

    private val mStartActivity = MutableLiveData<Boolean>()
    val startActivity : LiveData<Boolean> = mStartActivity

    private val mSignUpFailedError = MutableLiveData<String>()
    val signUpFailedError : LiveData<String> =  mSignUpFailedError

    fun signUpClickListener(user: User) {

        viewModelScope.launch(Dispatchers.IO) {

            repo.signUp( user ) {

                when(it) {

                    HttpStatusCode.OK -> mStartActivity.postValue(true)

                    HttpStatusCode.FAILED ->
                        mSignUpFailedError.postValue("Signup failed please try again later")

                    else -> {}
                }

            }
        }
    }
}