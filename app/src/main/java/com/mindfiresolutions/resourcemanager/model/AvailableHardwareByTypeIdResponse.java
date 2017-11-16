package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response pojo to get available hardware resource by resource id
 * Created by Shivangi Singh on 5/9/2017.
 */

public class AvailableHardwareByTypeIdResponse {
    @SerializedName("Response")
    private ResponseGetterBase mResponse;
    @SerializedName("AvailableHardwareDetails")
    private List<AvailableHardwareDetails> mAvailableHardwareDetails = null;

    public ResponseGetterBase getResponse() {
        return mResponse;
    }

    public void setResponse(ResponseGetterBase mResponse) {
        this.mResponse = mResponse;
    }

    public List<AvailableHardwareDetails> getAvailableHardwareDetails() {
        return mAvailableHardwareDetails;
    }

    public void setAvailableHardwareDetails(List<AvailableHardwareDetails> mAvailableHardwareDetails) {
        this.mAvailableHardwareDetails = mAvailableHardwareDetails;
    }

}

