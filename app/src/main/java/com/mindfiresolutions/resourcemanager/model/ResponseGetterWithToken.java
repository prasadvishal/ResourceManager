package com.mindfiresolutions.resourcemanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shivangi Singh on 3/30/2017
 */

public class ResponseGetterWithToken extends ResponseGetterBase {
    @SerializedName("Token")
    private Token token;
    @SerializedName("Role")
    private String role;
    @SerializedName("ID")
    private Integer ID;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setID (Integer id) {ID = id;}

    public Integer getID  (){return ID;}

}
