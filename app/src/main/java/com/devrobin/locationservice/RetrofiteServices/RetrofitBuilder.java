package com.devrobin.locationservice.RetrofiteServices;

import com.devrobin.locationservice.utils.Credentials;
import com.devrobin.locationservice.utils.LocationAPIServices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    //add GsonConverter and BaseUrl and build Retrofit
//    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
//            .baseUrl(Credentials.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create());
//
//    private static Retrofit retrofit = retrofitBuilder.build();
//
//    private static LocationAPIServices locationAPIServices = retrofit.create(LocationAPIServices.class);
//
//    public static LocationAPIServices getLocationAPIServices() {
//        return locationAPIServices;
//    }



    private static Retrofit retrofit;
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    public static Retrofit getClient(){

        if (retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }
}
