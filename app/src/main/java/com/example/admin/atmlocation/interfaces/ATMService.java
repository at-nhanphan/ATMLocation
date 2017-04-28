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
    @GET("api/search/atm.php")
    Call<APIResponse> getAllATM(@Query("lat") double lat, @Query("lng") double lng, @Query("radius") int radius);

    @GET("api/search/search.php")
    Call<APIResponse> getATMSearch(@Query("bank") String bank, @Query("district") String district);

    @GET("/maps/api/directions/json")
    Call<DirectionResult> getData(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);
}
