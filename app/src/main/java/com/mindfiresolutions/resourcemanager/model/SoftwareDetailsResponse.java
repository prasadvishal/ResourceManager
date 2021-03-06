package com.mindfiresolutions.resourcemanager.model;

/**
 * Created by Shivangi Singh on 4/11/2017
 */

import com.google.gson.annotations.SerializedName;

public class SoftwareDetailsResponse {

    @SerializedName("Response")
    private ResponseGetterBase response;
    @SerializedName("SoftwareDetails")
    private SoftwareDetails softwareDetails;
    @SerializedName("SoftwareKeyDetails")
    private String softwareKeyDetails;

    public String getSoftwareKeyDetails() {
        return softwareKeyDetails;
    }
    public void setSoftwareKeyDetails(String softwareKeyDetails) {
        this.softwareKeyDetails = softwareKeyDetails;
    }
    public ResponseGetterBase getResponse() {
        return response;
    }

    public void setResponse(ResponseGetterBase response) {
        this.response = response;
    }

    public SoftwareDetails getSoftwareDetails() {
        return softwareDetails;
    }

    public void setSoftwareDetails(SoftwareDetails softwareDetails) {
        this.softwareDetails = softwareDetails;
    }

}




