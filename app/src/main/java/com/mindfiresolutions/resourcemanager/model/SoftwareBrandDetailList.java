package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Shivangi Singh on 4/7/2017
 */


public class SoftwareBrandDetailList {

    @SerializedName("SoftwareBrandID")
    private Integer iD;
    @SerializedName("Brand")
    private String brand;
    @SerializedName("Description")
    private String description;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

