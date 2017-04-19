package com.example.admin.atmlocation.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ConfigRetrofit class
 * Created by Admin on 3/11/2017.
 */

public class ConfigRetrofit {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static Gson createGson() {
        return new GsonBuilder().setLenient()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }
}
