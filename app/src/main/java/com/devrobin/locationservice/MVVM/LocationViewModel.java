package com.devrobin.locationservice.MVVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LocationViewModel extends AndroidViewModel {

    private static LocationRepository repository;
    private LiveData<List<LocationData>> allLocations;
    private LiveData<LocationData> lastLocation;


    public LocationViewModel(@NonNull Application application) {
        super(application);

        repository = new LocationRepository(application);
        allLocations = repository.getAllLocations();
        lastLocation = repository.getLastLocation();
    }


//    public void insertLocation(LocationData locationData){
//        repository.insertLocation(locationData);
//    }
//
//    public void updateLocation(LocationData locationData){
//        repository.UpdateDataWithLocationEntity();
//    }

    public LiveData<List<LocationData>> getAllLocations(){
        return allLocations;
    }

    public LiveData<LocationData> getLastLocation(){
        return lastLocation;
    }

    public void DeleteLocations(){
        repository.DeleteLocations();
    }


    @Override
    protected void onCleared() {
        super.onCleared();

        repository.ShutDown();
    }
}
