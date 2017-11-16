package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * pojo to set contact number in EditUserProfile
 * Created by Shivangi Singh on 3/31/2017.
 */

public class ContactSetter {
    @SerializedName("Contact")
    private String mContact;

    public String getmContact() {
        return mContact;
    }

    public void setmContact(String contact) {
        mContact = contact;
    }


}
