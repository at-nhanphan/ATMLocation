package com.example.admin.findatm.models.googleDirections;

import com.google.gson.annotations.SerializedName;

/**
 * Distance class
 * Created by naunem on 11/04/2017.
 */

public class Distance {
    @SerializedName("text")
    private String text;
    @SerializedName("value")
    private String value;

    public Distance(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
