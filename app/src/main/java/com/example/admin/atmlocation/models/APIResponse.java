package com.example.admin.atmlocation.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 *
 * Created by Admin on 3/11/2017.
 */

public class APIResponse {
    @SerializedName("size")
    private int size;
    @SerializedName("data")
    private ArrayList<MyATM> myATMs;

    public APIResponse(ArrayList<MyATM> myATMs, int size) {
        this.myATMs = myATMs;
        this.size = size;
    }

    public ArrayList<MyATM> getMyATMs() {
        return myATMs;
    }

    public int getSize() {
        return size;
    }
}
