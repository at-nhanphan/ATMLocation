package com.example.admin.findatm.models.googleDirections;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Route class
 * Created by naunem on 12/04/2017.
 */

@Getter
@Setter
public class Route {
    @SerializedName("overview_polyline")
    private OverviewPolyLine overviewPolyLine;
    private ArrayList<Leg> legs;
}
