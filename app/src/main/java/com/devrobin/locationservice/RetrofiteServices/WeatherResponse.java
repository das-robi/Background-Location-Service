package com.devrobin.locationservice.RetrofiteServices;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {

    @SerializedName("name")
    private String cityName;

    @SerializedName("coord")
    private String coord;

    @SerializedName("main")
    private String main;

    @SerializedName("weather")
    private List<Weather> weathers;


    //Getters
    public String getCityName() {
        return cityName;
    }

    public String getCoord() {
        return coord;
    }

    public String getMain() {
        return main;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    //Inner classes
    public static class Weather {

        @SerializedName("main")
        private String mains;

        @SerializedName("description")
        private String description;

        public String getMains() {
            return mains;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class Coord{

        @SerializedName("lan")
        private double lan;

        @SerializedName("lon")
        private double lon;

        public double getLan() {
            return lan;
        }

        public double getLon() {
            return lon;
        }
    }

    public static class main{

        @SerializedName("temp")
        private String temp;

        @SerializedName("humidity")
        private String humidity;

        public String getTemp() {
            return temp;
        }

        public String getHumidity() {
            return humidity;
        }
    }
}
