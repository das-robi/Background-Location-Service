package com.devrobin.locationservice.MVVMROOM.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "location_table")
public class LocationData {

    @PrimaryKey(autoGenerate = true)
    private int locId;

    @SerializedName("cityName")
    private String cityName;

    @SerializedName("areaName")
    private String areaName;

    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;

    @SerializedName("timestamp")
    private long timestamp;

    public LocationData(String cityName, double lat, double lng, String areaName) {
        this.cityName = cityName;
        this.lat = lat;
        this.lng = lng;
        this.areaName = areaName;
    }

    //Getters and Setters
    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
