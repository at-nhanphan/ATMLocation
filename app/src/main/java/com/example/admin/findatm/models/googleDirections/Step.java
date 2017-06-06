package com.example.admin.findatm.models.googleDirections;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Step class
 * Created by naunem on 12/04/2017.
 */

@Setter
@Getter
public class Step implements Parcelable {
    @SerializedName("start_location")
    private MyLocation startLocation;
    @SerializedName("end_location")
    private MyLocation endLocation;
    @SerializedName("html_instructions")
    private String htmlInstructions;
    private String maneuver;
    private OverviewPolyLine polyline;
    private Distance distance;
    private Duration duration;

    private Step(Parcel in) {
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
