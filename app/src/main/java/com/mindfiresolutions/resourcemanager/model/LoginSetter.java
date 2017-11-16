package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shivangi Singh on 3/29/2017
 */

public class LoginSetter {
    @SerializedName("UserName")
    private String mUserName;
    @SerializedName("Password")
    private String mPassword;
    @SerializedName("ID")
    private Integer ID;

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public void setmID (Integer id) {ID = id;}

    public Integer getID  (){return ID;}
}
