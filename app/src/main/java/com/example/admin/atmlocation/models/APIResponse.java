package com.example.admin.atmlocation.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 *
 * Created by Admin on 3/11/2017.
 */

public class APIResponse {
    @SerializedName("data")
    ArrayList<MyATM> myATMs;

    public ArrayList<MyATM> getMyATMs() {
        return myATMs;
    }

    public void setMyATMs(ArrayList<MyATM> myATMs) {
        this.myATMs = myATMs;
    }
}
