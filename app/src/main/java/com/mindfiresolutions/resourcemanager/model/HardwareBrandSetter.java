package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishal Prasad on 4/6/2017.
 */

public class HardwareBrandSetter {
    @SerializedName("BrandID")
    private int id;
    @SerializedName("Brand")
    private String brand;
    @SerializedName("Description")
    private String desc;
    @SerializedName("CustomerCare")
    private String customerCare;
    @SerializedName("IsAvailable")
    private boolean isAvailable;

    public int getId() {
        return id;
    }

    public void setid(int iD) {
        this.id = iD;
    }

    public String getBrand() { return brand;}

    public void setBrand(String brand1) {
        this.brand = brand1;
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String des) {
        this.desc = des;
    }

    public String getCustomerCare() {
        return customerCare;
    }

    public void setCustomerCare(String customerCare1) {
        this.customerCare = customerCare1;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable1) {
        this.isAvailable = isAvailable1;
    }


}
