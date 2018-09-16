package com.kiwabolab.scotiabankloyalty.network;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestApiAdapter {
    //----------------------------------------------------------------------------------------------
    //Variables
    int cacheSize = 10 * 1024 * 1024; // 10MB
    //----------------------------------------------------------------------------------------------
    /**Recibe la url base del servidor*/
    public RestClient EstablecerConexion(String servidor, Context context){
        //building HTTP Client for Interceptor
        OkHttpClient.Builder okHttpbuilder = new OkHttpClient().newBuilder().cache(new Cache(context.getCacheDir(), cacheSize));
        //headers for the web services are handled on GearMeAppInterceptor
        okHttpbuilder.addInterceptor(new AppInterceptor());
        //Here should be included authentication parameters for request
        okHttpbuilder.retryOnConnectionFailure(false);
        okHttpbuilder.readTimeout(48, TimeUnit.SECONDS);
        okHttpbuilder.writeTimeout(60, TimeUnit.SECONDS);
        OkHttpClient client = okHttpbuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(servidor)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(RestClient.class);
    }
}