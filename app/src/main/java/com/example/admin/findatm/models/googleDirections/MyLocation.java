package com.example.admin.findatm.models.googleDirections;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * MyLocation class
 * Created by Admin on 3/11/2017.
 */

@Getter
@Setter
@AllArgsConstructor
public class MyLocation implements Parcelable {
    private double lat;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }
}
