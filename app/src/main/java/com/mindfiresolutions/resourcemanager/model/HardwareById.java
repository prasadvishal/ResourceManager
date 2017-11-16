package com.mindfiresolutions.resourcemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * This pojo is used in HardwareDetailsActivity
 * Created by Shivangi Singh on 4/17/2017
 * modified on 5/31/2017
 */

public class HardwareById implements Parcelable {

    @SerializedName("HardwareID")
    private int mId;
    @SerializedName("MACID")
    private String mMacId;
    @SerializedName("ServiceTag")
    private String mServiceTag;
    @SerializedName("TypeID")
    private int mTypeID;
    @SerializedName("Type")
    private String mType;
    @SerializedName("BrandID")
    private int mBrandId;
    @SerializedName("Brand")
    private String mBrand;
    @SerializedName("Model")
    private String mModel;
    @SerializedName("OrderDate")
    private String mOrderDate;
    @SerializedName("ReceivedDate")
    private String mReceivedDate;
    @SerializedName("Status")
    private String mStatus;
    @SerializedName("Description")
    private String mDescription;
    @SerializedName("CreationDate")
    private String mCreationDate;
    @SerializedName("CreatedBy")
    private String mCreatedBy;
    @SerializedName("CreatedByID")
    private String mModifiedDate;
    @SerializedName("ModifiedBy")
    private String mModifiedBy;
    @SerializedName("IsSharable")
    private Boolean isSharable;

    public int getID() {
        return mId;
    }

    public void setID(int mId) {
        this.mId = mId;
    }

    public String getMACID() {
        return mMacId;
    }

    public void setMACID(String mMacId) {
        this.mMacId = mMacId;
    }

    public String getServiceTag() {
        return mServiceTag;
    }

    public void setServiceTag(String mServiceTag) {
        this.mServiceTag = mServiceTag;
    }

    public int getTypeID() {
        return mTypeID;
    }

    public void setTypeID(int mTypeID) {
        this.mTypeID = mTypeID;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public int getBrandID() {
        return mBrandId;
    }

    public void setBrandID(int mBrandId) {
        this.mBrandId = mBrandId;
    }

    public String getBrand() {
        return mBrand;
    }

    public void setBrand(String mBrand) {
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

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(String mCreationDate) {
        this.mCreationDate = mCreationDate;
    }

    public String getCreatedBy() {
        return mCreatedBy;
    }

    public void setCreatedBy(String mCreatedBy) {
        this.mCreatedBy = mCreatedBy;
    }

    public String getModifiedDate() {
        return mModifiedDate;
    }

    public void setModifiedDate(String mModifiedDate) {
        this.mModifiedDate = mModifiedDate;
    }

    public String getModifiedBy() {
        return mModifiedBy;
    }

    public void setModifiedBy(String mModifiedBy) {
        this.mModifiedBy = mModifiedBy;
    }

    public Boolean getIsSharable() {
        return isSharable;
    }

    public void setIsSharable(Boolean isSharable) {
        this.isSharable = isSharable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMacId);
        dest.writeString(mServiceTag);
        dest.writeString(mOrderDate);
        dest.writeString(mReceivedDate);
        dest.writeString(mType);
        dest.writeString(mBrand);
        dest.writeString(mDescription);
    }

    /**
     * funtioc to generate instances of your Parcelable class from a Parcel.
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public HardwareById createFromParcel(Parcel in) {
            return new HardwareById(in);
        }

        public HardwareById[] newArray(int size) {
            return new HardwareById[size];
        }
    };

    /**
     * De-parcel object
     * @param in objects deparcable to repective types
     */
    private HardwareById(Parcel in) {
        mMacId = in.readString();
        mServiceTag = in.readString();
        mOrderDate = in.readString();
        mReceivedDate = in.readString();
        mType = in.readString();
        mBrand = in.readString();
        mDescription = in.readString();
    }
}
