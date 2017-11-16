package com.mindfiresolutions.resourcemanager.model;

/**
 * Created by Shivangi Singh on 4/5/2017.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HardwareSummaryWithResponse {

    @SerializedName("Response")
    private ResponseGetterBase response;
    @SerializedName("HardwareSummaryList")
    private List<HardwareSummaryList> hardwareSummaryList = null;

    public ResponseGetterBase getResponse() {
        return response;
    }

    public void setResponse(ResponseGetterBase response) {
        this.response = response;
    }

    public List<HardwareSummaryList> getHardwareSummaryList() {
        return hardwareSummaryList;
    }

    public void setHardwareSummaryList(List<HardwareSummaryList> hardwareSummaryList) {
        this.hardwareSummaryList = hardwareSummaryList;
    }

}