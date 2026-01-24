package com.devrobin.locationservice.MVVMROOM.DAODatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {LocationDatabase.class}, version = 1)
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
