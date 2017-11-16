package com.mindfiresolutions.resourcemanager.model;

/**
 * Created by Shivangi Singh on 4/12/2017
 */
;
import com.google.gson.annotations.SerializedName;

public class NewBrandSetter {

    @SerializedName("ID")
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

