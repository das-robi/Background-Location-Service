package com.devrobin.locationservice.utils;

import com.devrobin.locationservice.RetrofiteServices.LocationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationAPIServices {

    //URL = http://api.openweathermap.org/data/2.5/ weather ?id=524901&appid=d8996a21eae65193e982a50fc5187dc7
    @GET("weather")
    Call<LocationResponse> getLocations(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String appid,
            @Query("units") String units
    );

}
