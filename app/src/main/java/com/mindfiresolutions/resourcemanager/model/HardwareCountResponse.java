package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shivangi Singh on 4/5/2017.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * pojo to get count of hardware resources
 * Created by shivangi singh on 5/10/2017
 */
public class HardwareCountResponse {

    @SerializedName("Response")
    private ResponseGetterBase mResponse;
    @SerializedName("HardwareCountSummary")
    private List<ViewHardwareSummary> mHardwareCountSummary = null;

    public ResponseGetterBase getResponse() {
        return mResponse;
    }

    public void setResponse(ResponseGetterBase mResponse) {
        this.mResponse = mResponse;
    }

    public List<ViewHardwareSummary> getHardwareCountSummary() {
        return mHardwareCountSummary;
    }

    public void setHardwareCountSummary(List<ViewHardwareSummary> mHardwareCountSummary) {
        this.mHardwareCountSummary = mHardwareCountSummary;
    }

}
