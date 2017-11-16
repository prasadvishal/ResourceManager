package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * get all the requests made by user
 * Created by Shivangi Singh on 3/30/2017
 */


public class UserResponse {

    @SerializedName("Response")
    private ResponseGetterBase mResponse;
    @SerializedName("userProfile")
    private User mUserProfile;

    public ResponseGetterBase getResponse() {
        return mResponse;
    }

    public void setResponse(ResponseGetterBase mResponse) {
        this.mResponse = mResponse;
    }

    public User getUserProfile() {
        return mUserProfile;
    }

    public void setUserProfile(User mUserProfile) {
        this.mUserProfile = mUserProfile;
    }

}

