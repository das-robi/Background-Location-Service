package com.devrobin.locationservice.RetrofiteServices;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    //add GsonConverter and BaseUrl and build Retrofit
//    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
//            .baseUrl(Credentials.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create());
//
//    private static Retrofit retrofit = retrofitBuilder.build();
//
//    public static LocationAPIServices getLocationAPIServices() {
//        return retrofit.create(LocationAPIServices.class);
//    }



//    private static Retrofit retrofit;
//    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
//
//    public static Retrofit getClient(){
//
//        if (retrofit == null){
//
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//        }
//
//        return retrofit;
//    }


    private static final String BASE_URL = "https://api.openweathermap.org/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
    if (retrofit == null) {

        // OkHttp client with timeouts and logging
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        // Build Retrofit instance
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
        return retrofit;
}

/**
 * Get API Service instance
 *
 * @return ApiService for making weather API calls
 */
public static LocationAPIServices getApiService() {
    return getClient().create(LocationAPIServices.class);
    }

}
