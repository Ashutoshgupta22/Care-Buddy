package com.aspark.carebuddy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspark.carebuddy.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor( private val repo: Repository) : ViewModel() {

//    fun bookServiceClickListener(){
//
//        viewModelScope.launch(Dispatchers.IO) {
//
//            repo.bookService()
//        }
//    }



}