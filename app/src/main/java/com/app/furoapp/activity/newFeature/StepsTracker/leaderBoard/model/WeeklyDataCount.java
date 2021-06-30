
package com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeeklyDataCount {

    @SerializedName("count_steps")
    @Expose
    private String countSteps;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public String getCountSteps() {
        return countSteps;
    }

    public void setCountSteps(String countSteps) {
        this.countSteps = countSteps;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
