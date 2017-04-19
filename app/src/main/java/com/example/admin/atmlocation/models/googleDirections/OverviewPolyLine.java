package com.example.admin.atmlocation.models.googleDirections;

import com.google.gson.annotations.SerializedName;

/**
 * OverviewPolyLine class
 * Created by naunem on 12/04/2017.
 */
public class OverviewPolyLine {
    @SerializedName("points")
    private String points;

    public String getPoints() {
        return points;
    }
}
