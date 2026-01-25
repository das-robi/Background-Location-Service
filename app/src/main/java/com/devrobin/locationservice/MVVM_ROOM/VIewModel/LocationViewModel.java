package com.devrobin.locationservice.MVVM_ROOM.VIewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.devrobin.locationservice.MVVM_ROOM.Repository.LocationRepository;
import com.devrobin.locationservice.MVVM_ROOM.model.LocationData;

import java.util.List;

public class LocationViewModel extends AndroidViewModel {

    private static LocationRepository repository;
    private LiveData<List<LocationData>> allLocations;


    public LocationViewModel(@NonNull Application application) {
        super(application);

        repository = new LocationRepository(application);
        allLocations = repository.getAllLocations();
    }


    public void insertLocation(LocationData locationData){
        repository.insertLocation(locationData);
    }

    public LiveData<List<LocationData>> getAllLocations(){
        return allLocations;
    }
}
