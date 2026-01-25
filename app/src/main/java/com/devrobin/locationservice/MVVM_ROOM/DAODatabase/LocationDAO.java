package com.devrobin.locationservice.MVVM_ROOM.DAODatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.devrobin.locationservice.MVVM_ROOM.model.LocationData;

import java.util.List;

@Dao
public interface LocationDAO {

    @Insert
    void insertLocation(LocationData locationData);

    @Query("SELECT * FROM location_table ORDER BY timestamp DESC")
    LiveData<List<LocationData>> getAllLocations();
}
