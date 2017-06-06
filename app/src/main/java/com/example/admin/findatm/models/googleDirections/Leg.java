package com.example.admin.findatm.models.googleDirections;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Leg class
 * Created by naunem on 12/04/2017.
 */

@Getter
@Setter
public class Leg implements Parcelable{
    @SerializedName("start_address")
    private String startAddress;
    @SerializedName("end_address")
    private String endAddress;
    private Distance distance;
    private Duration duration;
    @SerializedName("start_location")
    private MyLocation startLocation;
    @SerializedName("end_location")
    private MyLocation endLocation;
    private ArrayList<Step> steps;

    private Leg(Parcel in) {
        startAddress = in.readString();
        endAddress = in.readString();
        startLocation = in.readParcelable(MyLocation.class.getClassLoader());
        endLocation = in.readParcelable(MyLocation.class.getClassLoader());
        steps = in.createTypedArrayList(Step.CREATOR);
    }

    public static final Creator<Leg> CREATOR = new Creator<Leg>() {
        @Override
        public Leg createFromParcel(Parcel in) {
            return new Leg(in);
        }

        @Override
        public Leg[] newArray(int size) {
            return new Leg[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startAddress);
        dest.writeString(endAddress);
        dest.writeParcelable(startLocation, flags);
        dest.writeParcelable(endLocation, flags);
        dest.writeTypedList(steps);
    }
}
