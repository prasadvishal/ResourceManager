package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * pojo to contain all the details to be added to the database
 * Created by Vishal Prasad on 4/11/2017.
 */

public class AddHardwareSetter {
    @SerializedName("MACID")
    private String mMacId;
    @SerializedName("ServiceTag")
    private String mServiceTag;
    @SerializedName("Type")
    private int mType;
    @SerializedName("Brand")
    private int mBrand;
    @SerializedName("Model")
    private String mModel;
    @SerializedName("OrderDate")
    private String mOrderDate;
    @SerializedName("ReceivedDate")
    private String mReceivedDate;
    @SerializedName("Description")
    private String mDescription;
    @SerializedName("CreatedBy")
    private int mCreatedBy;
    @SerializedName("IsSharable")
    private boolean mIsSharable;

    public String getMacId() {
        return mMacId;
    }

    public void setMacId(String mMacId) {
        this.mMacId = mMacId;
    }

    public String getServiceTag() {
        return mServiceTag;
    }

    public void setServiceTag(String mServiceTag) {
        this.mServiceTag = mServiceTag;
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public int getBrand() {
        return mBrand;
    }

    public void setBrand(int mBrand) {
        this.mBrand = mBrand;
    }

    public String getModel() {
        return mModel;
    }

    public void setModel(String mModel) {
        this.mModel = mModel;
    }

    public String getOrderDate() {
        return mOrderDate;
    }

    public void setOrderDate(String mOrderDate) {
        this.mOrderDate = mOrderDate;
    }

    public String getReceivedDate() {
        return mReceivedDate;
    }

    public void setReceivedDate(String mReceivedDate) {
        this.mReceivedDate = mReceivedDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getCreatedBy() {
        return mCreatedBy;
    }

    public void setCreatedBy(int mCreatedBy) {
        this.mCreatedBy = mCreatedBy;
    }

    public boolean getIsSharable() {
        return mIsSharable;
    }

    public void setIsSharable(boolean mIsSharable) {
        this.mIsSharable = mIsSharable;
    }

}
