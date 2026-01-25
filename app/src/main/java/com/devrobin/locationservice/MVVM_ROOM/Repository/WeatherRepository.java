package com.devrobin.locationservice.MVVM_ROOM.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devrobin.locationservice.RetrofiteServices.LocationResponse;
import com.devrobin.locationservice.RetrofiteServices.RetrofitBuilder;
import com.devrobin.locationservice.RetrofiteServices.WeatherResponse;
import com.devrobin.locationservice.utils.LocationAPIServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    private LocationAPIServices locationAPIServices;

    public WeatherRepository() {
        locationAPIServices = RetrofitBuilder.getClient().create(LocationAPIServices.class);
    }

    public LiveData<WeatherResponse> getWeatherByLatLon(double lat, double lon, String apiKey){

        MutableLiveData<List<WeatherResponse>> weatherData = new MutableLiveData<>();

        locationAPIServices.getLocations(lat, lon, apiKey,"metric")
                .enqueue(new Callback<LocationResponse>() {
                    @Override
                    public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {

                        if (response.isSuccessful() && response.body() != null){
                            weatherData.postValue((List<WeatherResponse>) response.body());
                        }
                        else {
                            weatherData.postValue(null);
                        }

                    }

                    @Override
                    public void onFailure(Call<LocationResponse> call, Throwable t) {
                        weatherData.postValue(null);
                    }
                });

        return null;
    }
}
