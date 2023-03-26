package com.aspark.carebuddy.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.R;
import com.aspark.carebuddy.databinding.MapLayoutBinding;
import com.aspark.carebuddy.model.LocationServicesProvider;
import com.aspark.carebuddy.presenter.MapPresenter;
import com.aspark.carebuddy.presenter.UserLoginPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements Contract.View.MapView{

    MapLayoutBinding binding;
    LatLng nowLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MapLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Contract.Presenter.PresenterMap presenterMap = new MapPresenter();
        presenterMap.getLastLocation(this,this,savedInstanceState);
    }

    private void showGoogleMaps(Bundle savedInstanceState, Location location) {

        MapsInitializer.initialize(this);
        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.onResume();
        binding.mapView.getMapAsync(googleMap -> {

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Set your location")
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {

                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;

            }
            googleMap.setMyLocationEnabled(true);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15),1000,null);

            googleMap.setOnCameraMoveStartedListener(i -> {

                Log.w("MapActivity", "showGoogleMaps: Camera move started" );

                if (marker != null) {
                    marker.setDraggable(false);

                    binding.addressView.setText("");
                    binding.addressView.setHint("Loading..");
                }
                else
                    Log.e("MapActivity", "showGoogleMaps: CameraMoveStartedListener  marker is null" );
            });

            googleMap.setOnCameraMoveListener(() -> {

                LatLng midLatLng = googleMap.getCameraPosition().target;
                if (marker != null) {

                    marker.setPosition(midLatLng);
                }
                else
                    Log.e("MapActivity", "showGoogleMaps: cameraMoveListener marker is null" );
            });

            googleMap.setOnCameraIdleListener(() -> {

                Log.w("MapActivity", "showGoogleMaps: Camera idle getting address" );

                LatLng midLatLng = googleMap.getCameraPosition().target;

                if (marker != null) {

                    marker.setPosition(midLatLng);
                    nowLatLng = marker.getPosition();

                    Log.i("MainActivity", "showGoogleMaps: nowLocation= "+
                            nowLatLng.longitude+" "+nowLatLng.latitude);

                    getAddressText(nowLatLng);
                }
                else
                    Log.e("MapActivity", "showGoogleMaps: cameraIdleListener marker is null" );
            });

        });

        binding.saveLocationBtn.setOnClickListener(view -> {

            Log.d("MapActivity", "showGoogleMaps: Save location btn clicked");

            if (nowLatLng != null) {

                location.setLatitude(nowLatLng.latitude);
                location.setLongitude(nowLatLng.longitude);

                Log.d("MapActivity", "showGoogleMaps: saved location= " +
                        location.getLatitude() + " " + location.getLongitude());

              Contract.Presenter.PresenterMap presenterMap = new MapPresenter();
              presenterMap.confirmLocationClickListener(this,
                      location.getLatitude(),location.getLongitude());

            }
            else {
                Log.e("MapActivity", "showGoogleMaps: saveLocationBtnListener nowLatLng is null" );
            }


        });

    }

    private void getAddressText(LatLng nowLatLng) {

        List<Address> addresses = null;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(nowLatLng.latitude,nowLatLng.longitude,1);

        } catch (IOException e) {

            e.printStackTrace();
        }

        if (addresses != null && !addresses.isEmpty()) {

            String address = addresses.get(0).getAddressLine(0);
            Log.d("TAG", "getAddressText() returned: " + address );

            binding.addressView.setText(address);
        }
        else
            Log.e("MapActivity", "getAddressText: Address returned is null" );

    }


    @Override
    public void showMaps(Bundle savedInstanceState, Location location) {

        Log.i("MapActivity", "showMaps: Checking if returned location is null");

        if (location != null) {

            showGoogleMaps(savedInstanceState, location);
        }
        else {
            Toast.makeText(this, "Couldn't determine location", Toast.LENGTH_SHORT).show();
        }
    }
}