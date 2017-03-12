package com.example.admin.atmloction;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 3/11/2017.
 */

public class Location {
    @SerializedName("lat")
    String lat;
    @SerializedName("lng")
    String lng;

    public Location(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
