package com.aspark.carebuddy.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspark.carebuddy.model.Nurse
import com.aspark.carebuddy.model.User
import com.aspark.carebuddy.repository.Repository
import com.aspark.carebuddy.retrofit.HttpStatusCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor( private val repo: Repository) : ViewModel() {

    private var mGetUserdataSuccess = MutableLiveData<Boolean>()
    val getUserdata: LiveData<Boolean> = mGetUserdataSuccess
    private var mTopNursesList = MutableLiveData<ArrayList<Nurse>>()
    val topNurseList: LiveData<ArrayList<Nurse>> = mTopNursesList
    private var mShowToast = MutableLiveData<String>()
    val showToast: LiveData<String> = mShowToast


    fun getUserdata(email: String) {

        viewModelScope.launch(Dispatchers.IO) {

            repo.getUserData(email) {

                when(it) {

                    HttpStatusCode.OK ->  {
                        mGetUserdataSuccess.postValue(true)
                        getTopNurses(User.currentUser.pincode)
                    }

                    else -> mShowToast.postValue("Couldn't load data")
                }
            }
        }
    }

    private fun getTopNurses(pincode: String) {

        viewModelScope.launch(Dispatchers.IO) {

            repo.getTopNurses(pincode) {

                mTopNursesList.postValue(it)
            }
        }
    }

    fun getSelfCare() {
    }

}