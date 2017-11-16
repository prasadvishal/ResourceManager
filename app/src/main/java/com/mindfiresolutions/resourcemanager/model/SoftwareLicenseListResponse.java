package com.mindfiresolutions.resourcemanager.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
 * Created by Shivangi Singh on 4/7/2017.
 */
public class SoftwareLicenseListResponse {
    @SerializedName("Response")
    private ResponseGetterBase response;
    @SerializedName("LicenseList")
    private List<SoftwareLicenseList> licenseList = null;

    public ResponseGetterBase getResponse() {
        return response;
    }

    public void setResponse(ResponseGetterBase response) {
        this.response = response;
    }

    public List<SoftwareLicenseList> getSoftwareLicenseList() {
        return licenseList;
    }

    public void setSoftwareLicenseList(List<SoftwareLicenseList> licenseList) {
        this.licenseList = licenseList;
    }

}


