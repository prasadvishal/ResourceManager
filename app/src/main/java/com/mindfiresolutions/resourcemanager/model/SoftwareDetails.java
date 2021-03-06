package com.mindfiresolutions.resourcemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shivangi Singh on 4/11/2017
 */

public class SoftwareDetails implements Parcelable{

    @SerializedName("SoftwareID")
    private Integer iD;
    @SerializedName("CreatedBy")
    private String createdBy;
    @SerializedName("CreationDate")
    private String creationDate;
    @SerializedName("Description")
    private String description;
    @SerializedName("LicenseType")
    private Integer licenseType;
    //TODO make it corresponding type afterwards
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
    @SerializedName("Brand")
    private String brand;
    @SerializedName("LicenseTypeName")
    private String licenseTypeName;

    public String getBrand() {return brand;}

    public void setBrand(String brand) {this.brand = brand;
    }
    public String getLicenseTypeName() {
        return licenseTypeName;
    }
    public void setLicenseTypeName(String licenseTypeName) {
        this.licenseTypeName = licenseTypeName;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(softwareName);
        dest.writeString(version);
        dest.writeString(creationDate);
        dest.writeString(description);
    }
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {

        public SoftwareDetails createFromParcel(Parcel in) {
            return new SoftwareDetails(in);
        }

        public SoftwareDetails[] newArray(int size) {
            return new SoftwareDetails[size];
        }
    };
    // "De-parcel object
    private SoftwareDetails(Parcel in) {
        softwareName = in.readString();
        version = in.readString();
        creationDate = in.readString();
        description = in.readString();
    }
}
