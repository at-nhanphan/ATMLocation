package com.example.admin.atmlocation.interfaces;

import com.example.admin.atmlocation.models.googleDirections.DirectionResult;
import com.example.admin.atmlocation.models.APIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * ATMService interface
 * Created by Admin on 3/11/2017.
 */

public interface ATMService {
    @GET("api/search/atm.php?getall")
    Call<APIResponse> getAllATM();

    @GET("api/search/search.php")
    Call<APIResponse> getATMSearch(@Query("bank") String bank, @Query("district") String district);

    @GET("place/textsearch/json")
    Call<APIResponse> getNearATM(@Query("location") String location, @Query("radius") String radius,
                                 @Query("types") String types, @Query("key") String key);

    @GET("/maps/api/directions/json")
    Call<DirectionResult> getData(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);
}
