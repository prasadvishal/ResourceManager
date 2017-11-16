package com.mindfiresolutions.resourcemanager.model;

/**
 * view summary of all the shared resources
 * Created by Shivangi Singh on 4/4/2017.
 */

import com.google.gson.annotations.SerializedName;

public class ViewSharedResource {

    @SerializedName("TypeId")
    private int mTypeId;
    @SerializedName("Count")
    private int mCount;
    @SerializedName("HardwareType")
    private String mHardwareType;

    public int getTypeId() {
        return mTypeId;
    }

    public void setTypeId(int mTypeId) {
        this.mTypeId = mTypeId;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int mCount) {
        this.mCount = mCount;
    }

    public String getHardwareType() {
        return mHardwareType;
    }

    public void setHardwareType(String mHardwareType) {
        this.mHardwareType = mHardwareType;
    }

}