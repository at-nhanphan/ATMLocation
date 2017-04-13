package com.example.admin.atmlocation.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Legs class
 * Created by naunem on 12/04/2017.
 */

public class Legs {
    @SerializedName("distance")
    private Distance distance;
    @SerializedName("duration")
    private Duration duration;
    @SerializedName("steps")
    private List<Steps> steps;

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

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public List<Steps> getSteps() {
        return steps;
    }
}
