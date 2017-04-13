package com.example.admin.atmlocation.models;

import com.google.gson.annotations.SerializedName;

/**
 * Duration class
 * Created by naunem on 11/04/2017.
 */

public class Duration {
    @SerializedName("text")
    private String text;
    @SerializedName("value")
    private String value;

    public Duration(String text, String mValue) {
        this.text = text;
        this.value = mValue;
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
