package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishal Prasad on 4/17/2017.
 */

public class NewUserRequestSetter {

    @SerializedName("Type")
    private Integer type;
    @SerializedName("UserId")
    private Integer userId;
    @SerializedName("Title")
    private String title;
    @SerializedName("RequestedDevice")
    private Integer requestedDevice;
    @SerializedName("ToDate")
    private String toDate;
    @SerializedName("Description")
    private String description;
    @SerializedName("AssignedTo")
    private Integer assignedTo;
    @SerializedName("HardwareID")
    private Integer hardwareID;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRequestedDevice() {
        return requestedDevice;
    }

    public void setRequestedDevice(Integer requestedDevice) {
        this.requestedDevice = requestedDevice;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Integer getHardwareID() {
        return hardwareID;
    }

    public void setHardwareID(Integer hardwareID) {
        this.hardwareID = hardwareID;
    }

}
