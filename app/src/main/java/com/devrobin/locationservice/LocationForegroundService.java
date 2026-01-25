package com.devrobin.locationservice;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.devrobin.locationservice.MVVM_ROOM.Repository.LocationRepository;
import com.devrobin.locationservice.MVVM_ROOM.VIewModel.WeatherViewModel;
import com.devrobin.locationservice.MVVM_ROOM.model.LocationData;
import com.devrobin.locationservice.utils.Credentials;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationForegroundService extends Service {

    private static final String CHANNEL_ID = "Location Service";

    private static FusedLocationProviderClient fusedLocationProviderClient;
    private static LocationRequest locationRequest;
    private static LocationCallback locationCallback;
    private static LocationRepository locationRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        startForeground(1, createNotification());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRepository = new LocationRepository(getApplication());

        //Immediat Locatin
        fetchImmediateLocation();

        createLocationRequest();
        createLocationCallback();

    }

    private void fetchImmediateLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            return;
        }

        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
                            saveLocation(location.getLatitude(), location.getLongitude());
                            showToast(location.getLatitude(), location.getLongitude());
                        }
                    }
                });
    }

    private void saveLocation(double latitude, double longitude) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                locationRepository.insertLocation(new LocationData(latitude, longitude, System.currentTimeMillis()));
            }
        }).start();

        WeatherViewModel weatherViewModel = new ViewModelProvider((ViewModelStoreOwner) getApplicationContext())
                .get(WeatherViewModel.class);

        weatherViewModel.fetchLatLon(latitude, longitude, Credentials.API_KEY);

    }

    private void showToast(double latitude, double longitude) {

        Toast.makeText(this, "Lat" + latitude + "Lon" + longitude, Toast.LENGTH_SHORT).show();
    }

    private void createLocationRequest() {

        locationRequest = new LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                5*60*1000
        ).build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkingLocationSettings();
        return START_STICKY;
    }

    private void checkingLocationSettings(){

        LocationSettingsRequest.Builder locationBuilder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true);

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);

        settingsClient.checkLocationSettings(locationBuilder.build())
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        startLocationUpdates();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ResolvableApiException){
                            Toast.makeText(LocationForegroundService.this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
        );

    }

    public void createLocationCallback(){

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {

                Location location = locationResult.getLastLocation();

                if (location != null){

                    double lat = location.getLatitude();
                    double lon = location.getLongitude();

                    //Toast lat and lon
                    Toast.makeText(LocationForegroundService.this, "Lat: " + lat +
                            "Lon: " + lon, Toast.LENGTH_SHORT).show();


                    //Save fetch data
                    LocationData locationData = new LocationData(
                            lat,
                            lon,
                            System.currentTimeMillis()
                    );

                    locationRepository.insertLocation(locationData);
                }

            }
        };

    }


    public Notification createNotification(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Location Services",
                    NotificationManager.IMPORTANCE_LOW
            );

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Location Active")
                .setContentText("Every 5 minute location update")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
