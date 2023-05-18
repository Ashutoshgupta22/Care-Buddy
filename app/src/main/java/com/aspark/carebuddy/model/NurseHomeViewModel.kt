package com.aspark.carebuddy.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NurseHomeViewModel: ViewModel() {

    var isNurseSignedIn = MutableLiveData<Boolean>()

    fun signInNurse() {
        isNurseSignedIn.value = true
    }

    fun signOutNurse() {
        isNurseSignedIn.value = false
    }


}