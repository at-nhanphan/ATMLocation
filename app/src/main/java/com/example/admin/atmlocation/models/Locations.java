package com.example.admin.atmlocation.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by Admin on 3/11/2017.
 */

public class Locations implements Parcelable {
    @SerializedName("lat")
    String lat;
    @SerializedName("lng")
    String lng;

    public Locations(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    protected Locations(Parcel in) {
        lat = in.readString();
        lng = in.readString();
    }

    public static final Creator<Locations> CREATOR = new Creator<Locations>() {
        @Override
        public Locations createFromParcel(Parcel in) {
            return new Locations(in);
        }

        @Override
        public Locations[] newArray(int size) {
            return new Locations[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lat);
        dest.writeString(lng);
    }
}
