package com.devrobin.locationservice.MVVMROOM.DAODatabase;

import androidx.room.Dao;
import androidx.room.Insert;

import com.devrobin.locationservice.MVVMROOM.model.LocationData;

@Dao
public interface LocationDAO {

    @Insert
    void insertLocation(LocationData locationData);


}
