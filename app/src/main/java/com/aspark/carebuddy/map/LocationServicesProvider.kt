package com.aspark.carebuddy.map

import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.LocationServices

class LocationServicesProvider(val context: Context) {

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }
    private var lastLocation: Location? = null

    fun getLastLocation(showMaps: (Location) -> Unit): Location?  {

        try {

            fusedLocationClient.lastLocation
                .addOnSuccessListener{ location: Location? ->

                    if (location != null) {

                        Log.d("LocationServiceProvider", "onSuccess() returned: " +
                                location.longitude + " " + location.latitude)

                        lastLocation = location
                       // mapView.showMaps(savedInstanceState, location)
                        showMaps(location)

                    } else {

                        //since we have used getLastLocation method
                        //location is null when previously no client has accessed location.
                        // It usually happens in emulator or new device. So open Maps at least once
                        // before calling this method.

                        //TODO to prevent this use getCurrentLocation method instead of getLastLocation.
                        Log.e("LocationServiceProvider", "getLastLocation: " +
                                "Error Occurred location is null")
                        lastLocation = null
                    }
                }
        } catch (exception: SecurityException) {

            Log.e("LocationServiceProvider", "getLastLocation: Location " +
                    "Permission denied",exception )
            lastLocation = null
        }

        return lastLocation
    }
}