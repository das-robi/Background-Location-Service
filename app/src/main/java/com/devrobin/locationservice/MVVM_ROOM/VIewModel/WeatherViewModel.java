package com.devrobin.locationservice.MVVM_ROOM.VIewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.devrobin.locationservice.MVVM_ROOM.Repository.WeatherRepository;
import com.devrobin.locationservice.RetrofiteServices.WeatherResponse;

import java.util.List;

public class WeatherViewModel extends ViewModel {

    private static WeatherRepository weatherRepository;
    private MutableLiveData<WeatherResponse> weatherLiveData;

    public WeatherViewModel(){
        weatherRepository = new WeatherRepository();
        weatherLiveData = new MutableLiveData<>();
    }

    //Fetch Latitude and Longitude
    public void fetchLatLon(double lan, double lon, String apiKey){
        LiveData<WeatherResponse> weatherResponses = weatherRepository.getWeatherByLatLon(lan, lon, apiKey);

        weatherResponses.observeForever(new Observer<WeatherResponse>() {
            @Override
            public void onChanged(WeatherResponse weatherResponse) {
                weatherLiveData.postValue(weatherResponse);
            }
        });
    }

    public LiveData<WeatherResponse> getWeatherLiveData(){
        return weatherLiveData;
    }
}
