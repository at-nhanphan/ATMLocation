package com.example.admin.findatm.models.googleDirections;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * MyLocation class
 * Created by Admin on 3/11/2017.
 */

public class MyLocation implements Parcelable {
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;

    private MyLocation(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<MyLocation> CREATOR = new Creator<MyLocation>() {
        @Override
        public MyLocation createFromParcel(Parcel in) {
            return new MyLocation(in);
        }

        @Override
        public MyLocation[] newArray(int size) {
            return new MyLocation[size];
        }
    };

    public MyLocation(double latitude, double longitude) {
        this.lat = latitude;
        this.lng = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @SuppressWarnings("SameReturnValue")
    public static Creator<MyLocation> getCREATOR() {
        return CREATOR;
    }
}
