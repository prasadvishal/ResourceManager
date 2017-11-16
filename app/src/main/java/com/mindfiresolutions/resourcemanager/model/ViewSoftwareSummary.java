package com.mindfiresolutions.resourcemanager.model;

/**
 * pojo to view the software summary
 * Created by Shivangi Singh on 4/4/2017.
 */

import com.google.gson.annotations.SerializedName;

public class ViewSoftwareSummary {
    @SerializedName("SoftwareID")
    private int mSoftwareID;
    @SerializedName("SoftwareName")
    private String mSoftwareName;
    @SerializedName("TotalKeys")
    private int mTotalKeys;
    @SerializedName("AvailableKeys")
    private int mAvailableKeys;
    @SerializedName("ValidUpto")
    private String mValidUpto;

    public int getAvailableKeys() {
        return mAvailableKeys;
    }

    public void setAvailableKeys(int mAvailableKeys) {
        this.mAvailableKeys = mAvailableKeys;
    }

    public String getSoftwareName() {
        return mSoftwareName;
    }

    public void setSoftwareName(String mSoftwareName) {
        this.mSoftwareName = mSoftwareName;
    }

    public int getSoftwareID() {
        return mSoftwareID;
    }

    public void setSoftwareID(int mSoftwareID) {
        this.mSoftwareID = mSoftwareID;
    }

    public int getValidCount() {
        return mTotalKeys;
    }

    public void setValidCount(Integer mTotalKeys) {
        this.mTotalKeys = mTotalKeys;
    }

    public String getValidUpto() {
        return mValidUpto;
    }

    public void setValidUpto(String mValidUpto) {
        this.mValidUpto = mValidUpto;
    }

}


