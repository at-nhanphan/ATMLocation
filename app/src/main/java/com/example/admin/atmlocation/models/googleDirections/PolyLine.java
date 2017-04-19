package com.example.admin.atmlocation.models.googleDirections;

import com.google.gson.annotations.SerializedName;

/**
 * PolyLine class
 * Created by naunem on 11/04/2017.
 */

public class PolyLine {
    @SerializedName("points")
    private String point;

    public PolyLine(String point) {
        this.point = point;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
