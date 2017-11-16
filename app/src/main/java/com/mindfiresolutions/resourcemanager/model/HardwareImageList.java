package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishal Prasad on 7/7/2017.
 */
public class HardwareImageList {

    @SerializedName("ImageID")
    private Integer imageID;
    @SerializedName("HardwareID")
    private Integer hardwareID;
    @SerializedName("ImageUrl")
    private String imageUrl;
    @SerializedName("Description")
    private Object description;
    @SerializedName("ImageName")
    private String imageName;

    public Integer getImageID() {
        return imageID;
    }

    public void setImageID(Integer imageID) {
        this.imageID = imageID;
    }

    public Integer getHardwareID() {
        return hardwareID;
    }

    public void setHardwareID(Integer hardwareID) {
        this.hardwareID = hardwareID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

}