package com.example.admin.findatm.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * Created by Admin on 3/11/2017.
 */

@Getter
@Setter
@AllArgsConstructor
public class APIResponse {
    private int size;
    @SerializedName("data")
    private ArrayList<MyATM> myATMs;
}
