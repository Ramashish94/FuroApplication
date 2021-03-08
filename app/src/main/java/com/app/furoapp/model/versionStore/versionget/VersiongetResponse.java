
package com.app.furoapp.model.versionStore.versionget;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersiongetResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("details")
    @Expose
    private Details details;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

}
