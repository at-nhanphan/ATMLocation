package com.example.admin.atmlocation.models;

import com.google.gson.annotations.SerializedName;

/**
 * Steps class
 * Created by naunem on 12/04/2017.
 */

public class Steps {
    @SerializedName("start_location")
    private Locations startLocation;
    @SerializedName("end_location")
    private Locations end_location;
    @SerializedName("polyline")
    private OverviewPolyLine polyline;

    public Locations getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Locations startLocation) {
        this.startLocation = startLocation;
    }

    public Locations getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Locations end_location) {
        this.end_location = end_location;
    }

    public OverviewPolyLine getPolyline() {
        return polyline;
    }

    public void setPolyline(OverviewPolyLine polyline) {
        this.polyline = polyline;
    }
}
