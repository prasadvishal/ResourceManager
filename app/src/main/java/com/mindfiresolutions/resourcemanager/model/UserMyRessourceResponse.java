package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vishal Prasad on 6/23/2017.
 */

public class UserMyRessourceResponse {
    @SerializedName("Response")
    private ResponseGetterBase response;
    @SerializedName("HardwareRequests")
    private List<UserRequest> hardwareRequests = null;
    @SerializedName("SoftwareRequests")
    private List<UserRequest> softwareRequests = null;
    @SerializedName("SharedResourceRequests")
    private List<UserRequest> sharedResourceRequests = null;

    public ResponseGetterBase getResponse() {
        return response;
    }

    public void setResponse(ResponseGetterBase response) {
        this.response = response;
    }

    public List<UserRequest> getHardwareRequests() {
        return hardwareRequests;
    }

    public void setHardwareRequests(List<UserRequest> hardwareRequests) {
        this.hardwareRequests = hardwareRequests;
    }

    public List<UserRequest> getSoftwareRequests() {
        return softwareRequests;
    }

    public void setSoftwareRequests(List<UserRequest> softwareRequests) {
        this.softwareRequests = softwareRequests;
    }

    public List<UserRequest> getSharedResourceRequests() {
        return sharedResourceRequests;
    }

    public void setSharedResourceRequests(List<UserRequest> sharedResourceRequests) {
        this.sharedResourceRequests = sharedResourceRequests;
    }

}