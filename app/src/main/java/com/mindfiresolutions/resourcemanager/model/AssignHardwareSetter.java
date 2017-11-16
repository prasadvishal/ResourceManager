package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Setter pojo for assigning hardware resource to user
 * Created by Shivangi Singh on 5/10/2017.
 */

public class AssignHardwareSetter {
    @SerializedName("UserID")
    private int mUserId;
    @SerializedName("HardwareID")
    private int mHardwareId;
    @SerializedName("AssignedDate")
    private String mAssignedDate;
    @SerializedName("AssignedBy")
    private int mAssignedBy;
    @SerializedName("Description")
    private String mDescription;

    public int getUserID() {
        return mUserId;
    }

    public void setUserID(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getHardwareID() {
        return mHardwareId;
    }

    public void setHardwareID(int mHardwareId) {
        this.mHardwareId = mHardwareId;
    }

    public String getAssignedDate() {
        return mAssignedDate;
    }

    public void setAssignedDate(String mAssignedDate) {
        this.mAssignedDate = mAssignedDate;
    }

    public int getAssignedBy() {
        return mAssignedBy;
    }

    public void setAssignedBy(int mAssignedBy) {
        this.mAssignedBy = mAssignedBy;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

}
