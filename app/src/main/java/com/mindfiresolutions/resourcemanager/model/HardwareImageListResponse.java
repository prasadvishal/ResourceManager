package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vishal Prasad on 7/7/2017.
 */

public class HardwareImageListResponse {
    @SerializedName("Response")

    private ResponseGetterBase response;
    @SerializedName("HardwareImageList")

    private List<HardwareImageList> hardwareImageList = null;

    public ResponseGetterBase getResponse() {
        return response;
    }

    public void setResponse(ResponseGetterBase response) {
        this.response = response;
    }

    public List<HardwareImageList> getHardwareImageList() {
        return hardwareImageList;
    }

    public void setHardwareImageList(List<HardwareImageList> hardwareImageList) {
        this.hardwareImageList = hardwareImageList;
    }

}