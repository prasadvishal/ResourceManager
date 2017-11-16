package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Pojo to get list of Brands which has been already registered to the organization
 * Created by Vishal Prasad on 4/6/2017.
 */

public class HardwareBrandResponse {
    @SerializedName("Response")
    private ResponseGetterBase mResponse;
    @SerializedName("HardwareBrandDetails")
    private List<HardwareBrandSetter> mHardwareBrandSetter = null;

    public ResponseGetterBase getResponse() {
        return mResponse;
    }

    public void setResponse(ResponseGetterBase mResponse) {
        this.mResponse = mResponse;
    }

    public List<HardwareBrandSetter> getHardwareBrandSetter() {
        return mHardwareBrandSetter;
    }

    public void setHardwareBrandSetter(List<HardwareBrandSetter> mHardwareBrandSetterList) {
        this.mHardwareBrandSetter = mHardwareBrandSetterList;
    }

}
