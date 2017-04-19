package com.example.admin.atmlocation.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.admin.atmlocation.models.googleDirections.Geometry;
import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by Admin on 3/3/2017.
 */

public class ATM implements Parcelable {

    @SerializedName("name")
    private String name;
    @SerializedName("formatted_address")
    private String address;
    @SerializedName("icon")
    private String icon;
    @SerializedName("place_id")
    private String place_id;
    @SerializedName("rating")
    private String rating;
    @SerializedName("geometry")
    private Geometry geometry;
    private boolean isFavorite;

    public ATM(String name, String address, String rating, boolean isFavorite) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.isFavorite = isFavorite;
    }

    public ATM(String name, String address, String icon, String place_id, String rating, Geometry geometry) {
        this.name = name;
        this.address = address;
        this.icon = icon;
        this.place_id = place_id;
        this.rating = rating;
        this.geometry = geometry;
    }

    private ATM(Parcel in) {
        name = in.readString();
        address = in.readString();
        icon = in.readString();
        place_id = in.readString();
        rating = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<ATM> CREATOR = new Creator<ATM>() {
        @Override
        public ATM createFromParcel(Parcel in) {
            return new ATM(in);
        }

        @Override
        public ATM[] newArray(int size) {
            return new ATM[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(icon);
        dest.writeString(place_id);
        dest.writeString(rating);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
