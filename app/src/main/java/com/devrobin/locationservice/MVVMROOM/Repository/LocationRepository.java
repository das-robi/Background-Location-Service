package com.devrobin.locationservice.MVVMROOM.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devrobin.locationservice.MVVMROOM.DAODatabase.LocationDAO;
import com.devrobin.locationservice.MVVMROOM.DAODatabase.LocationDatabase;
import com.devrobin.locationservice.MVVMROOM.model.LocationData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationRepository {

    private MutableLiveData<List<LocationData>> allLocation;
    private LocationDAO locationDAO;
    private ExecutorService executorService;

    public LocationRepository(Application application){

        LocationDatabase locationDatabase = LocationDatabase.getInstance(application);
        locationDAO = locationDatabase.locationDAO();

        executorService = Executors.newSingleThreadExecutor();
    }

    public void insertLocation(LocationData locationData){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                locationDAO.insertLocation(locationData);
            }
        });
    }

    public LiveData<List<LocationData>> getAllLocations(){
        return allLocation;
    }
}
