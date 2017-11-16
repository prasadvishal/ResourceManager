package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishal Prasad on 5/12/2017.
 */

public class UpdateSoftwareSetter {

    @SerializedName("SoftwareID")

    private Integer softwareID;
    @SerializedName("Description")

    private String description;
    @SerializedName("LicenseType")

    private Integer licenseType;
    @SerializedName("ModifiedBy")

    private String modifiedBy;
    @SerializedName("ModifiedDate")

    private String modifiedDate;
    @SerializedName("SoftwareBrand")

    private Integer softwareBrand;
    @SerializedName("SoftwareName")

    private String softwareName;
    @SerializedName("Version")

    private String version;

    public Integer getSoftwareID() {
        return softwareID;
    }

    public void setSoftwareID(Integer softwareID) {
        this.softwareID = softwareID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(Integer licenseType) {
        this.licenseType = licenseType;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getSoftwareBrand() {
        return softwareBrand;
    }

    public void setSoftwareBrand(Integer softwareBrand) {
        this.softwareBrand = softwareBrand;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
