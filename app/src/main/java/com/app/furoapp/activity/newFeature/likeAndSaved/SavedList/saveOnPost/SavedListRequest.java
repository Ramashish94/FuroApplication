package com.app.furoapp.activity.newFeature.likeAndSaved.SavedList.saveOnPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavedListRequest {

    @SerializedName("activity_id")
    @Expose
    private String activityId;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
