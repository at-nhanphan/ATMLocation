package com.example.admin.atmlocation.models.googleDirections;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * DirectionResult class
 * Created by naunem on 12/04/2017.
 */

public class DirectionResult {
    @SerializedName("routes")
    private ArrayList<Route> routes;

    public ArrayList<Route> getRoutes() {
        return routes;
    }

}
