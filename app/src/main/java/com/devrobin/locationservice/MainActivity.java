package com.devrobin.locationservice;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.devrobin.locationservice.MVVM_ROOM.VIewModel.LocationViewModel;
import com.devrobin.locationservice.MVVM_ROOM.VIewModel.WeatherViewModel;
import com.devrobin.locationservice.MVVM_ROOM.model.LocationData;
import com.devrobin.locationservice.RetrofiteServices.WeatherResponse;
import com.devrobin.locationservice.utils.Credentials;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private final String[] foregroundLocationsPermission = {Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.POST_NOTIFICATIONS};
//
//    private final String[] backgroundLocationsPermission = {Manifest.permission.ACCESS_BACKGROUND_LOCATION};
//
//    private LocationManager locationManager;
//    private PermissionManager permissionManager;
//
//
//    //Widgets
//    private Button backGroundBTN;
//    private Button foreGroundBTN;
    private TextView rsltTxtvie;

    private static final int LOCATION_PERMISSION_CODE = 100;

    private static Boolean apiCall = false;


    private LocationViewModel locationViewModel;
    private WeatherViewModel weatherViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//        backGroundBTN = findViewById(R.id.background);
//        foreGroundBTN = findViewById(R.id.foreground);
        rsltTxtvie = findViewById(R.id.results);

//        locationManager = LocationManager.getInstance(this);
//        permissionManager = PermissionManager.getInstance(this);

//        Intent intent = new Intent(this, LocationForegroundService.class);
//        startForegroundService(intent);

        //Set OnClink Listener
//        foreGroundBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (permissionManager.checkPermission(foregroundLocationsPermission)){
//                    permissionManager.askPermission(MainActivity.this, foregroundLocationsPermission, 100);
//                }
//                else {
//                    Toast.makeText(MainActivity.this, "Foreground Location is enable" +
//                            "Now ask to the Background Locations", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//        backGroundBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (permissionManager.checkPermission(backgroundLocationsPermission)){
//                    permissionManager.askPermission(MainActivity.this, backgroundLocationsPermission, 200);
//                }
//                else {
//                    if (locationManager.isLocationEnable()){
//                        startLocationWork();
//                    }
//                    else {
//                        locationManager.CreateLocationRequest();
//                        Toast.makeText(MainActivity.this, "Location service is not enable", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }});

        //ViewModel
        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);


        checkPermission();

        observeLocations();
        observeWeather();


    }

    private void checkPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_CODE
            );

            return;
        }

        Intent intent = new Intent(this, LocationForegroundService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }

        if (!isGpsEnabled()){
            Toast.makeText(this, "Please turn on your Location", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startLocationService();
        }
    }

    private boolean isGpsEnabled() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startLocationService(){
        Intent intent = new Intent(this, LocationForegroundService.class);
        startForegroundService(intent);
    }

    private void observeWeather() {

        weatherViewModel.getWeatherLiveData().observe(this, new Observer<WeatherResponse>() {
            @Override
            public void onChanged(WeatherResponse weatherResponse) {
                updateWeatherInfo(weatherResponse);
            }
        });

    }

    private void observeLocations() {
        locationViewModel.getAllLocations().observe(this, new Observer<List<LocationData>>() {
            @Override
            public void onChanged(List<LocationData> locationData) {


                if (locationData != null && !locationData.isEmpty()){

                    double lat = locationData.get(0).getLat();
                    double lon = locationData.get(0).getLng();

                    weatherViewModel.fetchLatLon(lat, lon, Credentials.API_KEY);
                }

            }
        });
    }


    private void updateWeatherInfo(WeatherResponse weatherResponse) {

        if (weatherResponse != null && weatherResponse.getMain() != null){

            String info = "city" + weatherResponse.getCityName()
                    + "\n Weather: " + weatherResponse.getWeathers().get(0).getDescription();

            rsltTxtvie.setText(info);
        }

        else{
            rsltTxtvie.setText("Unable to fetch");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);


        if (requestCode == LOCATION_PERMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }
        else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);
//
//        if (requestCode == 100 && permissionManager.handlerPermission(MainActivity.this, grantResults)){
//            Toast.makeText(this, "Foreground Location is granted " +
//                    "Now ask to the background Location", Toast.LENGTH_SHORT).show();
//        }
//        else if (requestCode == 200 && permissionManager.handlerPermission(MainActivity.this, grantResults)){
//            startLocationWork();
//        }
//
//    }
//
//    private void startLocationWork() {
//
//        WorkRequest foregroundWorkRequest = new OneTimeWorkRequest.Builder(BackgroundLocationWork.class)
//                .addTag("LocationWork")
//                .setBackoffCriteria(BackoffPolicy.LINEAR,
//                        OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.SECONDS)
//                .build();
//
//        WorkManager.getInstance(MainActivity.this).enqueue(foregroundWorkRequest);
//
//    }
}