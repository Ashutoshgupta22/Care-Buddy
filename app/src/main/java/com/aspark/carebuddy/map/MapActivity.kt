package com.aspark.carebuddy.map

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aspark.carebuddy.databinding.ActivityMapBinding
import com.aspark.carebuddy.ui.home.HomeActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private val viewModel: MapViewModel by viewModels()
    private var nowLatLng: LatLng? = null
    private var pincode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getLastLocation( LocationServicesProvider(this))

        viewModel.startActivity.observe(this){

            it?.let {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        viewModel.lastLocation.observe(this){

            if (it == null) {
                Toast.makeText(this, "Couldn't determine location",
                    Toast.LENGTH_SHORT).show()
                finish()
            }

            else  showGoogleMaps(savedInstanceState,it)
        }
    }
    private fun showGoogleMaps(savedInstanceState: Bundle?, location: Location) {

        MapsInitializer.initialize(this)
        binding.mapView.onCreate(savedInstanceState)
        //binding.mapView.onResume()
        binding.mapView.getMapAsync { googleMap: GoogleMap ->
            val latLng = LatLng(location.latitude, location.longitude)
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("Set your location")
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )
//            if (ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED) {
//
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return@getMapAsync
//            }

            try {
                googleMap.isMyLocationEnabled = true
            }
            catch (e: SecurityException){
                Log.e("MapActivity", "showGoogleMaps: Location Permission denied" )
            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f), 1000,
                null)

            googleMap.setOnCameraMoveStartedListener {

                Log.w("MapActivity", "showGoogleMaps: Camera move started")
                if (marker != null) {
                    marker.isDraggable = false
                    binding.addressView.text = ""
                    binding.addressView.hint = "Loading.."
                } else Log.e(
                    "MapActivity",
                    "showGoogleMaps: CameraMoveStartedListener  marker is null"
                )
            }

            googleMap.setOnCameraMoveListener {
                val midLatLng = googleMap.cameraPosition.target
                if (marker != null) {
                    marker.position = midLatLng
                } else Log.e("MapActivity", "showGoogleMaps: cameraMoveListener " +
                        "marker is null")
            }

            googleMap.setOnCameraIdleListener {
                Log.w("MapActivity", "showGoogleMaps: Camera idle getting address")
                val midLatLng = googleMap.cameraPosition.target
                if (marker != null) {
                    marker.position = midLatLng
                    nowLatLng = marker.position
                    Log.i(
                        "MainActivity", "showGoogleMaps: nowLocation= " +
                                nowLatLng!!.longitude + " " + nowLatLng!!.latitude
                    )
                    getAddressText(nowLatLng!!)
                } else Log.e("MapActivity", "showGoogleMaps: cameraIdleListener " +
                        "marker is null")
            }
        }

        binding.saveLocationBtn.setOnClickListener {
            Log.d("MapActivity", "showGoogleMaps: Save location btn clicked")

            if (nowLatLng != null) {

                location.latitude = nowLatLng!!.latitude
                location.longitude = nowLatLng!!.longitude

                Log.d("MapActivity", "showGoogleMaps: saved location= " +
                        location.latitude + " " + location.longitude)

                viewModel.confirmLocationClickListener(location,pincode)

            } else {
                Log.e("MapActivity", "showGoogleMaps: saveLocationBtnListener" +
                        " nowLatLng is null")
            }
        }
    }

    private fun getAddressText(nowLatLng: LatLng) {
        var addresses: List<Address>? = null
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            addresses = geocoder.getFromLocation(nowLatLng.latitude,
                nowLatLng.longitude, 1)

        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (!addresses.isNullOrEmpty()) {

            val address = addresses[0].getAddressLine(0)
            pincode = addresses[0].postalCode
            Log.d("TAG", "getAddressText() returned: $address")
            Log.d("TAG", "Pincode $pincode")
            binding.addressView.text = address
        }
        else Log.e("MapActivity", "getAddressText: Address returned is null")
    }

    override fun onResume() {
        super.onResume()

        Log.i("MapActivity", "onResume: map activity resumed")
    }

}