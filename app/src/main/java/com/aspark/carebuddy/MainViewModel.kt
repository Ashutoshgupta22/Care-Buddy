package com.aspark.carebuddy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var startUserHomeActivity = MutableLiveData<Boolean>()
    var startNurseHomeActivity = MutableLiveData<Boolean>()
    var startUserLoginActivity = MutableLiveData<Boolean>()
    var startNurseLoginActivity = MutableLiveData<Boolean>()

    fun callUserHomeActivity() {

        startUserHomeActivity.value = true

    }

    fun callNurseHomeActivity() {
        startNurseHomeActivity.value = true

    }

    fun callUserLoginActivity() {
        startUserLoginActivity.value = true

    }

    fun callNurseLoginActivity() {
        startNurseLoginActivity.value = true

    }


}