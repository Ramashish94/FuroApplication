
package com.app.furoapp.activity.newFeature.ContentEngagementModel.feedHomeFragment_ListingNew;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivitiesListing {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
