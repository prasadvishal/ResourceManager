package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * class to set software id
 * Created by Shivangi Singh on 6/6/2017.
 */

public class SoftwareIdSetter {
    @SerializedName("SoftwareID")
    private int mSoftwareId;

    public int getSoftwareId() {
        return mSoftwareId;
    }

    public void setSoftwareId(int softwareId) {
        this.mSoftwareId = softwareId;
    }
}
