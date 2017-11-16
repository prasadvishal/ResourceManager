package com.mindfiresolutions.resourcemanager.model;

/**
 * Created by Vishal Prasad on 3/31/2017.
 */

import com.google.gson.annotations.SerializedName;


public class SignUpSetter extends LoginSetter {

        @SerializedName("Firstname")
        private String mFirstname;
        @SerializedName("Lastname")
        private String mLastName;
        @SerializedName("EmployeeID")
        private String mEmployeeID;
        @SerializedName("Email")
        private String mEmail;
        @SerializedName("Role")
        private int mRole;
        @SerializedName("Contact")
        private String mContact;

        public String getFirstname() {
            return mFirstname;
        }

        public void setFirstName(String firstName) {
            mFirstname = firstName;
        }

        public String getLastName() {
            return mLastName;
        }

        public void setLastName(String lastName) {
            mLastName = lastName;
        }

        public String getEmployeeID() {
            return mEmployeeID;
        }

        public void setEmployeeID(String employeeID) {
            mEmployeeID = employeeID;
        }

        public String getEmail() {
            return mEmail;
        }

        public void setEmail(String email) {
            mEmail = email;
        }

        public int getRole() {
            return mRole;
        }

        public void setRole(int role) {
            mRole = role;
        }

        public String getContact() {
            return mContact;
        }

        public void setContact(String contact) {
            mContact = contact;
        }


    }

