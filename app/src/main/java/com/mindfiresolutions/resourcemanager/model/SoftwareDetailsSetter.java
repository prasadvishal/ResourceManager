package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Response;

/**
 * Created by Vishal Prasad on 4/14/2017.
 */

public class SoftwareDetailsSetter {
        @SerializedName("Response")
        private ResponseGetterBase response;
        @SerializedName("SoftwareDetailsList")
        private List<SoftwareDetails> softwareDetailsList = null;

        public ResponseGetterBase getResponse() {
            return response;
        }

        public void setResponse(ResponseGetterBase response) {
            this.response = response;
        }

        public List<SoftwareDetails> getSoftwareDetailsList() {
            return softwareDetailsList;
        }

        public void setSoftwareDetailsList(List<SoftwareDetails> softwareDetailsList) {
            this.softwareDetailsList = softwareDetailsList;
        }
    }
