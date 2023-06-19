package com.aspark.carebuddy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NurseHomeViewModel: ViewModel() {

    private var _isNurseSignedIn = MutableLiveData<Boolean>()
    val isNurseSignedIn: LiveData<Boolean> = _isNurseSignedIn

    fun signInNurse() {
        _isNurseSignedIn.value = true
    }

    fun signOutNurse() {
        _isNurseSignedIn.value = false
    }


}