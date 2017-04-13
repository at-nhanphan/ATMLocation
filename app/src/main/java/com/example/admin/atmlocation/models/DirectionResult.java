package com.example.admin.atmlocation.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * DirectionResult class
 * Created by naunem on 12/04/2017.
 */

public class DirectionResult {
    @SerializedName("routes")
    private List<Routes> routes;

    public List<Routes> getRoutes() {
        return routes;
    }

}
