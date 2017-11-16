package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vishal Prasad on 6/8/2017.
 */

public class HardwareListForUserResponse {
    @SerializedName("Response")
    private ResponseGetterBase response;
    @SerializedName("AssignedHardwares")
    private List<AssignedHardware> assignedHardwares = null;

    public ResponseGetterBase getResponse() {
        return response;
    }

    public void setResponse(ResponseGetterBase response) {
        this.response = response;
    }

    public List<AssignedHardware> getAssignedHardwares() {
        return assignedHardwares;
    }

    public void setAssignedHardwares(List<AssignedHardware> assignedHardwares) {
        this.assignedHardwares = assignedHardwares;
    }

}
