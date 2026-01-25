package com.devrobin.locationservice.RetrofiteServices;

import com.google.gson.annotations.SerializedName;

public class LocationResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("lat")
    private double lat;

    @SerializedName("lon")
    private double lng;

    @SerializedName("country")
    private String country;

    @SerializedName("name")
    private String name;


    //Getters


    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "LocationResponse{" +
                "id=" + id +
                ", lat=" + lat +
                ", lng=" + lng +
                ", country='" + country + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
