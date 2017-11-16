package com.mindfiresolutions.resourcemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Pojo to set pending hardware resource
 * Created by Shivangi Singh on 5/8/2017
 */

public class AssignPendingHardwareSetter implements Parcelable {

    @SerializedName("UserID")
    private int mUserID;
    @SerializedName("HardwareID")
    private int mHardwareID;
    @SerializedName("RequestID")
    private int mRequestID;
    @SerializedName("AssignedBy")
    private int mAssignedBy;
    @SerializedName("Description")
    private String mDescription;

    public int getUserID() {
        return mUserID;
    }

    public void setUserID(int mUserID) {
        this.mUserID = mUserID;
    }

    public int getHardwareID() {
        return mHardwareID;
    }

    public void setHardwareID(int mHardwareID) {
        this.mHardwareID = mHardwareID;
    }

    public int getRequestID() {
        return mRequestID;
    }

    public void setRequestID(int mRequestID) {
        this.mRequestID = mRequestID;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mUserID);
        dest.writeInt(mHardwareID);
        dest.writeInt(mRequestID);
        dest.writeInt(mAssignedBy);
        dest.writeString(mDescription);
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public AssignPendingHardwareSetter createFromParcel(Parcel in) {
            return new AssignPendingHardwareSetter(in);
        }

        public AssignPendingHardwareSetter[] newArray(int size) {
            return new AssignPendingHardwareSetter[size];
        }
    };

    /**
     * De-parcel object
     * @param in can be deparceled to repective type
     */
    private AssignPendingHardwareSetter(Parcel in) {
        mUserID = in.readInt();
        mHardwareID = in.readInt();
        mRequestID = in.readInt();
        mAssignedBy = in.readInt();
        mDescription = in.readString();
    }

    public AssignPendingHardwareSetter(){}
}
