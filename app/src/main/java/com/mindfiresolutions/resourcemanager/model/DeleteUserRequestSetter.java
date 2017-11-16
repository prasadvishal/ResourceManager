package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishal Prasad on 5/17/2017.
 */

public class DeleteUserRequestSetter {
    @SerializedName("RequestID")

    private Integer requestID;
    @SerializedName("ResourceType")

    private Integer resourceType;

    public Integer getRequestID() {
        return requestID;
    }

    public void setRequestID(Integer requestID) {
        this.requestID = requestID;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }


}
