
package com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyDataCount {

    @SerializedName("count_steps")
    @Expose
    private String countSteps;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("user")
    @Expose
    private User user;

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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}