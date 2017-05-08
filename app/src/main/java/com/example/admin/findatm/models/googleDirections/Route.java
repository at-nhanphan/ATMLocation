package com.example.admin.findatm.models.googleDirections;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Route class
 * Created by naunem on 12/04/2017.
 */

public class Route {
    @SerializedName("overview_polyline")
    private OverviewPolyLine overviewPolyLine;
    @SerializedName("legs")
    private ArrayList<Leg> legs;

    public OverviewPolyLine getOverviewPolyLine() {
        return overviewPolyLine;
    }

    public ArrayList<Leg> getLegs() {
        return legs;
    }
}
