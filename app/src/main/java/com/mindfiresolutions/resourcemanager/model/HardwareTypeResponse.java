package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vishal Prasad on 4/9/2017.
 */

public class HardwareTypeResponse {
    @SerializedName("Response")
    private ResponseGetterBase response;
    @SerializedName("HardwareType")
    private List<HardwareTypeSetter> hardwareTypeSetter = null;

    public ResponseGetterBase getResponse() {
        return response;
    }

    public void setResponse(ResponseGetterBase response) {
        this.response = response;
    }

    public List<HardwareTypeSetter> getHardwareTypeSetter() {
        return hardwareTypeSetter;
    }

    public void setHardwareTypeSetter(List<HardwareTypeSetter> hardwareTypeSetterList) {
        this.hardwareTypeSetter = hardwareTypeSetterList;
    }
}
