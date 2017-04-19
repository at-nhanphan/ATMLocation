package com.example.admin.atmlocation.models.googleDirections;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Leg class
 * Created by naunem on 12/04/2017.
 */

public class Leg implements Parcelable{
    @SerializedName("start_address")
    private String startAddress;
    @SerializedName("end_address")
    private String endAddress;
    @SerializedName("distance")
    private Distance distance;
    @SerializedName("duration")
    private Duration duration;
    @SerializedName("start_location")
    private MyLocation startLocation;
    @SerializedName("end_location")
    private MyLocation endLocation;
    @SerializedName("steps")
    private ArrayList<Step> steps;

    protected Leg(Parcel in) {
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

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public MyLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(MyLocation startLocation) {
        this.startLocation = startLocation;
    }

    public MyLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(MyLocation endLocation) {
        this.endLocation = endLocation;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

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
