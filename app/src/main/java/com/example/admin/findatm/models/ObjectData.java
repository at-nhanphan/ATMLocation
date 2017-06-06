package com.example.admin.findatm.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Getter;

/**
 * Created by naunem on 06/06/2017.
 */

@Getter
public class ObjectData implements Parcelable{
    private List<MyATM> mMyATMs;

    public ObjectData(Parcel in) {
        mMyATMs = in.createTypedArrayList(MyATM.CREATOR);
    }

    public static final Creator<ObjectData> CREATOR = new Creator<ObjectData>() {
        @Override
        public ObjectData createFromParcel(Parcel in) {
            return new ObjectData(in);
        }

        @Override
        public ObjectData[] newArray(int size) {
            return new ObjectData[size];
        }
    };

    public ObjectData(List<MyATM> mListAtms) {
        this.mMyATMs = mListAtms;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mMyATMs);
    }
}
