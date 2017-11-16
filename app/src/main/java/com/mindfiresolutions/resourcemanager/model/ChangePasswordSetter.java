package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishal Prasad on 3/31/2017.
 */

public class ChangePasswordSetter {
    @SerializedName("UserName")
    private String mUserName;
    @SerializedName("Password")
    private String mPassword;
    @SerializedName("NewPassword")
    private String mNewPassword;

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {mPassword = password; }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }
    public String getNewPassword() {
        return mNewPassword;
    }

    public void setNewPassword(String newpassword) {mNewPassword = newpassword; }
}

