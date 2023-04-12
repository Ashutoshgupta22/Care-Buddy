package com.aspark.carebuddy.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.aspark.carebuddy.Contract;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationServicesProvider {

    private final FusedLocationProviderClient locationProviderClient;
     private Location lastLocation = null;
    Context context;

    public LocationServicesProvider(Context cont) {

        context = cont;
        locationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public Location getLastLocation(Contract.View.MapView mapView, Bundle savedInstanceState) {

        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.w("LocationServicesProvider", "getLastLocation: Permission not granted");
            return null;
        }

        locationProviderClient.getLastLocation().addOnSuccessListener((Activity) this.context, location -> {

            if (location != null){

                Log.d("LocationServiceProvider", "onSuccess() returned: " + location.getLongitude() +" "+location.getLatitude());
                lastLocation = location;
                Log.i("LocationServicesProvider", "getLastLocation: calling mapActivity methods");
                mapView.showMaps(savedInstanceState,location);
            }
            else {

                //since we have used getLastLocation method
                //location is null when previously no client has accessed location.
                // It usually happens in emulator or new device. So open Maps at least once before calling this method.

                //TODO to prevent this use getCurrentLocation method instead of getLastLocation.

                Log.e("LocationServiceProvider", "getLastLocation: Error Occurred location is null" );
                lastLocation = null;

            }
        });

        return lastLocation;
    }
}
