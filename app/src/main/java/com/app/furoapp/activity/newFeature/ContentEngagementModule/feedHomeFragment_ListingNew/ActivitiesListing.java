
package com.app.furoapp.activity.newFeature.ContentEngagementModule.feedHomeFragment_ListingNew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivitiesListing {

    @SerializedName("data")
    @Expose
    private PaginationData data ;
    @SerializedName("status")
    @Expose
    private String status;

    public PaginationData getData() {
        return data;
    }

    public void setData(PaginationData data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
