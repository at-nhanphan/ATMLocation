package com.example.admin.atmloction;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 3/11/2017.
 */

public class APIResponse {
    @SerializedName("results")
    ArrayList<ATM> atms;
    @SerializedName("status")
    String status;

    public ArrayList<ATM> getAtms() {
        return atms;
    }

    public void setAtms(ArrayList<ATM> atms) {
        this.atms = atms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
