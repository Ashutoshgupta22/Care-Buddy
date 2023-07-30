package com.aspark.carebuddy.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspark.carebuddy.model.Nurse
import com.aspark.carebuddy.repository.Repository
import com.aspark.carebuddy.retrofit.HttpStatusCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor( private val repo: Repository) : ViewModel() {

    private var _getNurse = MutableLiveData<Nurse>()
    val getNurse: LiveData<Nurse> = _getNurse

    fun getNurseById(id: Int) {

        viewModelScope.launch(Dispatchers.IO) {

            repo.getNurseById(id) { code, nurse ->

                when(code) {
                    HttpStatusCode.OK -> {
                        _getNurse.postValue(nurse)
                    }
                   else -> {}
                }
            }

        }
    }

}