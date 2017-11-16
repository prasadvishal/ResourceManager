package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishal Prasad on 4/14/2017.
 */

public class AdminListSetter {
    @SerializedName("ID")
    private int id;
    @SerializedName("UserName")
    private String Username;
    @SerializedName("FirstName")
    private String firstname;
    @SerializedName("LastName")
    private String lastname;

    public int getId() {
        return id;
    }

    public void setid(int iD) {
        this.id = iD;
    }

    public String getUsername() { return Username;}

    public void setUsername(String type1) {
        this.Username = type1;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String des) {
        this.firstname = des;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastnamee(String lname) {
        this.lastname = lname;
    }
}
