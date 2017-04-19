package com.example.admin.atmlocation.services;

import com.example.admin.atmlocation.configs.DirectionRetrofit;
import com.example.admin.atmlocation.interfaces.ATMService;

/**
 * ApiUtils class
 * Created by naunem on 11/04/2017.
 */

public class ApiUtils {
    private static final String URL = "https://maps.googleapis.com/";

    public static ATMService getService() {
        return DirectionRetrofit.getClient(URL).create(ATMService.class);
    }
}
