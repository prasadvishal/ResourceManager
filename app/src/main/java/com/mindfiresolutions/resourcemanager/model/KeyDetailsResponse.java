package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shivangi Singh on 6/6/2017.
 */

public class KeyDetailsResponse {
        @SerializedName("Response")
        private ResponseGetterBase response;
        @SerializedName("SoftwareKeyList")
        private List<SoftwareKeyList> softwareKeyList = null;

        public ResponseGetterBase getResponse() {
            return response;
        }

        public void setResponse(ResponseGetterBase response) {
            this.response = response;
        }

        public List<SoftwareKeyList> getSoftwareKeyList() {
            return softwareKeyList;
        }

        public void setSoftwareKeyList(List<SoftwareKeyList> softwareKeyList) {
            this.softwareKeyList = softwareKeyList;
        }
    }



