package com.example.admin.atmlocation.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 *
 * Created by Admin on 3/11/2017.
 */

public class APIResponse {
    @SerializedName("data")
    private ArrayList<MyATM> myATMs;

    public APIResponse(ArrayList<MyATM> myATMs) {
        this.myATMs = myATMs;
    }

    public ArrayList<MyATM> getMyATMs() {
        return myATMs;
    }
}
