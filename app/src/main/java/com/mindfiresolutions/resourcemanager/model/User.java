package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * pojo to get user UserDetails
 * Created by Shivangi Singh on 3/28/2017
 */

public class User {

    @SerializedName("UserName")
    private String mUserName;
    @SerializedName("FirstName")
    private String mFirstName;
    @SerializedName("LastName")
    private String mLastName;
    @SerializedName("EmployeeID")
    private String mEmployeeID;
    @SerializedName("Email")
    private String mEmail;
    @SerializedName("Role")
    private String mRole;
    @SerializedName("Contact")
    private String mContact;
    @SerializedName("ImageUrl")
    private String mImageUrl;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getEmployeeID() {
        return mEmployeeID;
    }

    public void setEmployeeID(String mEmployeeID) {
        this.mEmployeeID = mEmployeeID;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String mRole) {
        this.mRole = mRole;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String mContact) {
        this.mContact = mContact;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

}
