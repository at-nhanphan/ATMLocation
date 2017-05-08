package com.example.admin.findatm.services;

import com.example.admin.findatm.configs.DirectionRetrofit;
import com.example.admin.findatm.interfaces.ATMService;

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
