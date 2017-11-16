package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shivangi Singh on 5/25/2017
 */

public class AllResourceRequestSummary {

    @SerializedName("Response")
    private ResponseGetterBase mResponse;
    @SerializedName("HardwareRequests")
    private List<UserRequest> mHardwareRequests = null;
    @SerializedName("SoftwareRequests")
    private List<UserRequest> mSoftwareRequests = null;
    @SerializedName("SharedResourceRequests")
    private List<UserRequest> mSharedResourceRequests = null;

    public ResponseGetterBase getResponse() {
        return mResponse;
    }

    public void setResponse(ResponseGetterBase mResponse) {
        this.mResponse = mResponse;
    }

    public List<UserRequest> getHardwareRequests() {
        return mHardwareRequests;
    }

    public void setUserHardwareRequests(List<UserRequest> HardwareRequests) {
        this.mHardwareRequests = HardwareRequests;
    }

    public List<UserRequest> getSoftwareRequests() {
        return mSoftwareRequests;
    }

    public void setSoftwareRequests(List<UserRequest> mSoftwareRequests) {
        this.mSoftwareRequests = mSoftwareRequests;
    }

    public List<UserRequest> getSharedResourceRequests() {
        return mSharedResourceRequests;
    }

    public void setSharedResourceRequests(List<UserRequest> mSharedResourceRequests) {
        this.mSharedResourceRequests = mSharedResourceRequests;
    }

}
