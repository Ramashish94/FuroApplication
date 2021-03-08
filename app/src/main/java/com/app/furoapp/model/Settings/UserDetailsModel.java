package com.app.furoapp.model.Settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailsModel {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("user")
    @Expose
    private UserModel user;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
