package com.example.admin.atmlocation.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Routes class
 * Created by naunem on 12/04/2017.
 */

public class Routes {
    @SerializedName("overview_polyline")
    private OverviewPolyLine overviewPolyLine;
    @SerializedName("legs")
    private List<Legs> legs;

    public OverviewPolyLine getOverviewPolyLine() {
        return overviewPolyLine;
    }

    public List<Legs> getLegs() {
        return legs;
    }
}
