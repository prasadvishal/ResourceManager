package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shivangi Singh on 4/7/2017.
 */

public class SoftwareBrandListResponse {

        @SerializedName("Response")
        private ResponseGetterBase response;
        @SerializedName("SoftwareBrandDetailsList")
        private List<SoftwareBrandDetailList> softwareBrandDetailsList = null;

        public ResponseGetterBase getResponse() {
            return response;
        }

        public void setResponse(ResponseGetterBase response) {
            this.response = response;
        }


        public List<SoftwareBrandDetailList> getSoftwareBrandDetailsList() {
            return softwareBrandDetailsList;
        }

        public void setSoftwareBrandDetailsList(List<SoftwareBrandDetailList> softwareBrandDetailsList) {
            this.softwareBrandDetailsList = softwareBrandDetailsList;
        }

    }

