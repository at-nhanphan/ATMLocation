package com.example.admin.atmlocation.interfaces;

import com.example.admin.atmlocation.services.APIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * Created by Admin on 3/11/2017.
 */

public interface ATMService {
    @GET("place/textsearch/json")
    Call<APIResponse> getATM(@Query("query") String atm, @Query("key") String key);

    @GET("place/textsearch/json")
    Call<APIResponse> getNearATM(@Query("location") String location, @Query("radius") String radius,
                                 @Query("types") String types, @Query("key") String key);
}
