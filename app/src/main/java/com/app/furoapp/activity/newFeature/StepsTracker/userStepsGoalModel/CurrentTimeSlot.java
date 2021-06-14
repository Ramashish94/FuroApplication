
package com.app.furoapp.activity.newFeature.StepsTracker.userStepsGoalModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentTimeSlot {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("calories")
    @Expose
    private String calories;
    @SerializedName("count_steps")
    @Expose
    private String countSteps;
    @SerializedName("total_steps")
    @Expose
    private String totalSteps;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getCountSteps() {
        return countSteps;
    }

    public void setCountSteps(String countSteps) {
        this.countSteps = countSteps;
    }

    public String getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(String totalSteps) {
        this.totalSteps = totalSteps;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
