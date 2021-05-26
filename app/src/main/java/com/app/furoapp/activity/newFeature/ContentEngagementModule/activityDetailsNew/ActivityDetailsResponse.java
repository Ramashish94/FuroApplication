
package com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityDetailsResponse {

    @SerializedName("activity_details")
    @Expose
    private List<ActivityDetail> activityDetails = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<ActivityDetail> getActivityDetails() {
        return activityDetails;
    }

    public void setActivityDetails(List<ActivityDetail> activityDetails) {
        this.activityDetails = activityDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
