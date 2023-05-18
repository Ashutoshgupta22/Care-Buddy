package com.aspark.carebuddy.map

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aspark.carebuddy.api.UserApi
import com.aspark.carebuddy.model.User.Companion.currentUser
import com.aspark.carebuddy.retrofit.RetrofitService
import com.aspark.carebuddy.retrofit.request.LocationData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapViewModel: ViewModel() {

    private val mLastLocation = MutableLiveData<Location>()
     val lastLocation: LiveData<Location>
        get() = mLastLocation
    private val mStartActivity = MutableLiveData<Boolean>()
    val startActivity: LiveData<Boolean>
        get() = mStartActivity

    fun getLastLocation( locationServicesProvider: LocationServicesProvider) {

        locationServicesProvider.getLastLocation{

            mLastLocation.value = it
        }
    }

    fun confirmLocationClickListener(location: Location, pincode: String?) {

        Log.i("MapViewModel", "confirmLocationClickListener: called")

        saveLocation(location.latitude, location.longitude, pincode)
    }

    private fun saveLocation(latitude: Double, longitude: Double, pincode: String?) {

        val retrofitService = RetrofitService()
        val userApi = retrofitService.retrofit.create(UserApi::class.java)

        val locationData = LocationData()
        locationData.latitude = latitude
        locationData.longitude = longitude
        locationData.pincode = pincode

        Log.i("MapViewModel", "saveLocation: getCurrentUser " + currentUser.email)
        locationData.email = currentUser.email

        userApi.saveLocation(locationData)
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {

                    //TODO showing response successful when currentUser is null.
                    if (response.isSuccessful && response.body() == true) {

                        Log.i("MapViewModel", "onResponse: saveLocation Successful")
                        mStartActivity.value = true

                    } else {
                        Log.e("MapViewModel",
                            "onResponse: saveLocation Response FAILED code= " +
                                    response.code() + response.message())
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.e("MapViewModel", "onFailure: saveLocation FAILED: " + t.message)
                }
            })
    }

}