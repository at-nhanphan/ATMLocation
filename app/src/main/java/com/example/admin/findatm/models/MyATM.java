package com.example.admin.findatm.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * MyATM class model
 * Created by naunem on 19/04/2017.
 */

public class MyATM implements Parcelable {
    @SerializedName("madiadiem")
    private String addressId;
    @SerializedName("tendiadiem")
    private String addressName;
    @SerializedName("diachi")
    private String address;
    @SerializedName("maquan")
    private String districtId;
    @SerializedName("manganhang")
    private String bankId;
    @SerializedName("lat")
    private String lat;
    @SerializedName("lng")
    private String lng;
    private boolean favorite;
    private int id;

    public MyATM(String addressId, String addressName, String address, String districtId, String bankId, String lat, String lng, int id) {
        this.addressId = addressId;
        this.addressName = addressName;
        this.address = address;
        this.districtId = districtId;
        this.bankId = bankId;
        this.lat = lat;
        this.lng = lng;
        this.id = id;
    }

    public MyATM() {

    }

    public MyATM(int id, String addressId, String addressName, String address, String districtId, String bankId, String lat, String lng, boolean favorite) {
        this.id = id;
        this.addressId = addressId;
        this.addressName = addressName;
        this.address = address;
        this.districtId = districtId;
        this.bankId = bankId;
        this.lat = lat;
        this.lng = lng;
        this.favorite = favorite;
    }

    protected MyATM(Parcel in) {
        addressId = in.readString();
        addressName = in.readString();
        address = in.readString();
        districtId = in.readString();
        bankId = in.readString();
        lat = in.readString();
        lng = in.readString();
        favorite = in.readByte() != 0;
        id = in.readInt();
    }

    public static final Creator<MyATM> CREATOR = new Creator<MyATM>() {
        @Override
        public MyATM createFromParcel(Parcel in) {
            return new MyATM(in);
        }

        @Override
        public MyATM[] newArray(int size) {
            return new MyATM[size];
        }
    };

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getAddressName() {
        return addressName;
    }

    public String getAddress() {
        return address;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addressId);
        dest.writeString(addressName);
        dest.writeString(address);
        dest.writeString(districtId);
        dest.writeString(bankId);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeByte((byte) (favorite ? 1 : 0));
        dest.writeInt(id);
    }
}
