package com.aspark.carebuddy.presenter;

import static com.aspark.carebuddy.model.UserModel.getCurrentUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.model.LocationServicesProvider;
import com.aspark.carebuddy.model.UserModel;
import com.aspark.carebuddy.retrofit.LocationData;
import com.aspark.carebuddy.retrofit.RetrofitService;
import com.aspark.carebuddy.retrofit.UserApi;
import com.aspark.carebuddy.view.user.UserHomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapPresenter implements Contract.Presenter.PresenterMap {

    LocationServicesProvider locationServicesProvider;
    Contract.View.MapView mapView;
    Location location;

    @Override
    public void getLastLocation(Context context, Contract.View.MapView mapView, Bundle savedInstanceState) {

        this.mapView = mapView;

        locationServicesProvider = new LocationServicesProvider(context);

        location = locationServicesProvider.getLastLocation(mapView,savedInstanceState);


    }

    @Override
    public void confirmLocationClickListener(Context context, double latitude, double longitude) {

        Log.i("MapPresenter", "confirmLocationClickListener: called");
        saveLocation(context,latitude,longitude);
    }

    private void saveLocation(Context context,double latitude, double longitude) {

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        LocationData locationData =  new LocationData();
        locationData.setLatitude(latitude);
        locationData.setLongitude(longitude);

        Log.i("MapPresenter", "saveLocation: getCurrentUser "+ getCurrentUser().getEmail());
        locationData.setUsername(getCurrentUser().getEmail());

        userApi.saveLocation(locationData)
                .enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        //TODO showing response successful when currentUser is null.

                        if (response.isSuccessful()) {


                            Log.i("MapPresenter", "onResponse: saveLocation Successful");

                            Intent intent = new Intent(context, UserHomeActivity.class);
                            context.startActivity(intent);
                            ((Activity)context).finish();
                        }
                        else {
                            Log.e("MapPresenter", "onResponse: saveLocation Response FAILED code= "+response.code()+response.message() );
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                        Log.e("MapPresenter", "onFailure: saveLocation FAILED: "+t.getMessage() );
                    }
                });
    }


}
