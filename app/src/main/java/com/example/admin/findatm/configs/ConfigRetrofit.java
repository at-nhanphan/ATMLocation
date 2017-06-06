package com.example.admin.findatm.configs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ConfigRetrofit class
 * Created by Admin on 3/11/2017.
 */

public class ConfigRetrofit {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(com.example.admin.findatm.services.ATMServiceImpl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
