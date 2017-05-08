package com.example.admin.findatm.models.googleDirections;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Step class
 * Created by naunem on 12/04/2017.
 */

public class Step implements Parcelable {
    @SerializedName("start_location")
    private MyLocation startLocation;
    @SerializedName("end_location")
    private MyLocation endLocation;
    @SerializedName("html_instructions")
    private String htmlInstructions;
    @SerializedName("maneuver")
    private String maneuver;
    @SerializedName("polyline")
    private OverviewPolyLine polyline;
    @SerializedName("distance")
    private Distance distance;
    @SerializedName("duration")
    private Duration duration;

    protected Step(Parcel in) {
        startLocation = in.readParcelable(MyLocation.class.getClassLoader());
        endLocation = in.readParcelable(MyLocation.class.getClassLoader());
        htmlInstructions = in.readString();
        maneuver = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

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

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public String getManeuver() {
        return maneuver;
    }

    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }

    public OverviewPolyLine getPolyline() {
        return polyline;
    }

    public void setPolyline(OverviewPolyLine polyline) {
        this.polyline = polyline;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(startLocation, flags);
        dest.writeParcelable(endLocation, flags);
        dest.writeString(htmlInstructions);
        dest.writeString(maneuver);
    }
}
