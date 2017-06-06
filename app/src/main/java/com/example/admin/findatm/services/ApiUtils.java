package com.example.admin.findatm.services;

import com.example.admin.findatm.configs.DirectionRetrofit;
import com.example.admin.findatm.interfaces.ATMService;

/**
 * ApiUtils class
 * Created by naunem on 11/04/2017.
 */

public class ApiUtils {
    public static final String URL = "https://maps.googleapis.com/";

    public static ATMService getService() {
        return DirectionRetrofit.getClient().create(ATMService.class);
    }
}
