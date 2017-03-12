package com.example.admin.atmloction;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Admin on 3/11/2017.
 */

public interface ATMService {
    @GET("place/textsearch/json")
    Call<APIResponse> getATM(@Query("query") String atm, @Query("key") String key);

}
