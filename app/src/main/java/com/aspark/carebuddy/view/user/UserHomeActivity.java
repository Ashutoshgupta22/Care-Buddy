package com.aspark.carebuddy.view.user;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.R;
import com.aspark.carebuddy.databinding.ActivityUserHomeBinding;
import com.aspark.carebuddy.model.LocationServicesProvider;
import com.aspark.carebuddy.model.UserModel;
import com.aspark.carebuddy.presenter.UserHomePresenter;
import com.aspark.carebuddy.view.MainActivity;
import com.aspark.carebuddy.view.MapActivity;

public class UserHomeActivity extends AppCompatActivity implements View.OnClickListener {

    boolean isSignedIn = true;
    ActivityUserHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUserSignedIn();

        //TODO currentUser is null when app is opened after destroying it.
        Log.i("UserHomeActivity", "onCreate: currentUser: "+ UserModel.getCurrentUser());

        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {

                    Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION,false);
                    Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,false);

                  //  LocationServicesProvider locationServicesProvider = new LocationServicesProvider(this);

                    if(fineLocationGranted != null && fineLocationGranted) {

                        Toast.makeText(this, "Fine location granted", Toast.LENGTH_SHORT).show();
                        //locationServicesProvider.getLastLocation(this, mapView, savedInstanceState);

                        Intent intent = new Intent(this,MapActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else if( coarseLocationGranted !=null && coarseLocationGranted) {

                        Toast.makeText(this, "Coarse Location Granted", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Location Permission denied", Toast.LENGTH_SHORT).show();


                    }
                });

//        locationPermissionRequest.launch(new String[]{
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//        });

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Checked that permission was GRANTED", Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(this, "onCreate: checked that permission was DENIED", Toast.LENGTH_SHORT).show();
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }

        binding.signOutBtn.setOnClickListener( view -> {

            Log.w("UserHomeActivity", "onCreate: User Logged Out" );

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            isSignedIn = false;
            setUserSignedIn();
            finish();
        });

        binding.locationIcon.setOnClickListener(this);
        binding.homeText.setOnClickListener(this);
        binding.downArrowIcon.setOnClickListener(this);
        binding.bookServiceBtn.setOnClickListener(view -> {

            Log.d("UserHomeActivity", "bookService: button clicked ");
            Contract.Presenter.PresenterUserHome presenterUserHome = new UserHomePresenter();
            presenterUserHome.bookService(view.getContext());

        });


    }

    private void setUserSignedIn() {

        SharedPreferences preferences = getSharedPreferences(getPackageName(),MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Log.i("UserHomeActivity", "setUserSignedIn: isSignedIn "+isSignedIn);

        editor.putBoolean("isSignedIn",isSignedIn);

        if (isSignedIn)
               editor.putString("username",UserModel.getCurrentUser().getEmail());
        else
               editor.putString("username",null);

        editor.apply();
    }


    @Override
    public void onClick(View view) {




    }
}