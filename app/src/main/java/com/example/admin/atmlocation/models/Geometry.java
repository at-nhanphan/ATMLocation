package com.example.admin.atmlocation.models;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by Admin on 3/11/2017.
 */

public class Geometry {
    @SerializedName("location")
    Location location;

    public Geometry(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
