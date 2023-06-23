package com.aspark.carebuddy.map

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspark.carebuddy.model.User.Companion.currentUser
import com.aspark.carebuddy.repository.Repository
import com.aspark.carebuddy.retrofit.HttpStatusCode
import com.aspark.carebuddy.retrofit.request.LocationData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor( private val repo: Repository): ViewModel() {

    private val mLastLocation = MutableLiveData<Location>()
     val lastLocation: LiveData<Location> = mLastLocation
    private val mStartActivity = MutableLiveData<Boolean>()
    val startActivity: LiveData<Boolean> = mStartActivity

    fun getLastLocation( locationServicesProvider: LocationServicesProvider) {

        viewModelScope.launch(Dispatchers.Default) {

            locationServicesProvider.getLastLocation{

                mLastLocation.postValue(it)
            }
        }

    }

    fun confirmLocationClickListener(location: Location, pincode: String?) {

        Log.i("MapViewModel", "confirmLocationClickListener: called")

        val locationData = LocationData( location.latitude, location.longitude,
            pincode, currentUser.email)

        viewModelScope.launch( Dispatchers.IO ) {

            repo.saveLocation(locationData) {

                when(it) {

                    HttpStatusCode.OK -> mStartActivity.postValue(true)

                    else -> {}
                }
            }
        }
    }
}