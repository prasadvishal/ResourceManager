package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * pojo to get details of available hardwares only
 * Created by Shivangi Singh on 5/9/2017.
 */

public class AvailableHardwareDetails {

    @SerializedName("HardwareID")
    private int hardwareID;
    @SerializedName("MACID")
    private String mACID;
    @SerializedName("ServiceTag")
    private String mServiceTag;
    @SerializedName("TypeID")
    private int mTypeId;
    @SerializedName("Type")
    private String mType;
    @SerializedName("BrandID")
    private int mBrandId;
    @SerializedName("Brand")
    private String brand;
    @SerializedName("Model")
    private String model;

    public int getHardwareID() {
        return hardwareID;
    }

    public void setHardwareID(int hardwareID) {
        this.hardwareID = hardwareID;
    }

    public String getMACID() {
        return mACID;
    }

    public void setMACID(String mACID) {
        this.mACID = mACID;
    }

    public String getServiceTag() {
        return mServiceTag;
    }

    public void setServiceTag(String mServiceTag) {
        this.mServiceTag = mServiceTag;
    }

    public int getTypeID() {
        return mTypeId;
    }

    public void setTypeID(int mTypeId) {
        this.mTypeId = mTypeId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public int getBrandID() {
        return mBrandId;
    }

    public void setBrandID(int mBrandId) {
        this.mBrandId = mBrandId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}
