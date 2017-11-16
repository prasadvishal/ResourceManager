package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vishal Prasad on 5/14/2017.
 */

public class UserMyRequestsResponse {
    @SerializedName("Response")

    private ResponseGetterBase response;
    @SerializedName("HardwareRequests")

    private List<UserHardwareRequest> userHardwareRequests = null;
    @SerializedName("SoftwareRequests")

    private List<UserSoftwareRequest> userSoftwareRequests = null;
    @SerializedName("SharedResourceRequests")

    private List<UserSharedRequest> userSharedRequests = null;

    public ResponseGetterBase getResponse() {
        return response;
    }

    public void setResponse(ResponseGetterBase response) {
        this.response = response;
    }

    public List<UserHardwareRequest> getUserHardwareRequests() {
        return userHardwareRequests;
    }

    public void setUserHardwareRequests(List<UserHardwareRequest> userHardwareRequests) {
        this.userHardwareRequests = userHardwareRequests;
    }

    public List<UserSoftwareRequest> getUserSoftwareRequests() {
        return userSoftwareRequests;
    }

    public void setUserSoftwareRequests(List<UserSoftwareRequest> userSoftwareRequests) {
        this.userSoftwareRequests = userSoftwareRequests;
    }

    public List<UserSharedRequest> getUserSharedRequests() {
        return userSharedRequests;
    }

    public void setUserSharedRequests(List<UserSharedRequest> userSharedRequests) {
        this.userSharedRequests = userSharedRequests;
    }

}
