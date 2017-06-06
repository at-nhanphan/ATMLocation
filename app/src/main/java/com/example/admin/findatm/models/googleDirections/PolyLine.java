package com.example.admin.findatm.models.googleDirections;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * PolyLine class
 * Created by naunem on 11/04/2017.
 */

@Setter
@Getter
@AllArgsConstructor
class PolyLine {
    @SerializedName("points")
    private String point;
}
