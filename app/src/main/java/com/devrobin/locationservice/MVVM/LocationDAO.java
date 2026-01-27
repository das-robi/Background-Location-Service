package com.devrobin.locationservice.MVVM;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LocationDAO {

    //insert new location
    @Insert
    long insertLocation(LocationData locationData);

    //Updat existing location
    @Update
    void updateLocation(LocationData locationData);

    @Query("SELECT * FROM location_table ORDER BY timestamp DESC")
    LiveData<List<LocationData>> getAllLocations();

    //Get recent Location
    @Query("SELECT * FROM location_table ORDER BY timestamp DESC LIMIT 1")
    LiveData<LocationData> getLastLocations();

    @Query("SELECT * FROM location_table WHERE id =:id ")
    LocationData getLocationById(int id);
    

    @Query("DELETE FROM location_table")
    void DeleteLocation();


}
