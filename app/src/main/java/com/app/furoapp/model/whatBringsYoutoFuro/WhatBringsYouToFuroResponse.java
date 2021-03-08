
package com.app.furoapp.model.whatBringsYoutoFuro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WhatBringsYouToFuroResponse {

    @SerializedName("user_reason")
    @Expose
    private UserReason userReason;
    @SerializedName("status")
    @Expose
    private String status;

    public UserReason getUserReason() {
        return userReason;
    }

    public void setUserReason(UserReason userReason) {
        this.userReason = userReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
