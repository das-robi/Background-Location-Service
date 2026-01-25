package com.devrobin.locationservice.MVVM_ROOM.DAODatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.devrobin.locationservice.MVVM_ROOM.model.LocationData;

@Database(entities = {LocationData.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {

    public abstract LocationDAO locationDAO();

    private static LocationDatabase instance;


    public static synchronized LocationDatabase getInstance(Context context){

        if (instance == null){

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    LocationDatabase.class, "locationDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

}
