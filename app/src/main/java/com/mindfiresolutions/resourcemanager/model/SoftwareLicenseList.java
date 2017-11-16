package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shivangi Singh on 4/7/2017.
 */

public class SoftwareLicenseList {


    @SerializedName("LicenseID")
    private Integer iD;
    @SerializedName("LicenseDetails")
    private String licenseDetails;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getLicenseDetails() {
        return licenseDetails;
    }

    public void setLicenseDetails(String licenseDetails) {
        this.licenseDetails = licenseDetails;
    }

}
