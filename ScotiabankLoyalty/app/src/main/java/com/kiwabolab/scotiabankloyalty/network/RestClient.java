package com.kiwabolab.scotiabankloyalty.network;

import com.kiwabolab.scotiabankloyalty.model.Product;
import com.kiwabolab.scotiabankloyalty.model.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestClient {
    //----------------------------------------------------------------------------------------------
    //Variables
    //----------------------------------------------------------------------------------------------
    //
    @GET("liststores")
    Call<List<Store>> getStores();
    //----------------------------------------------------------------------------------------------
    //
    @GET("productos/{val}")
    Call<List<Product>> getProducts(@Path("val") String taskId);
    //----------------------------------------------------------------------------------------------
    //

}