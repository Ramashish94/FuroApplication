
package com.app.furoapp.activity.newFeature.StepsTracker.historyModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllTimeCounter {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("calories")
    @Expose
    private Integer calories;
    @SerializedName("count_steps")
    @Expose
    private Integer countSteps;
    @SerializedName("total_steps")
    @Expose
    private Integer totalSteps;
    @SerializedName("daily_average")
    @Expose
    private Object dailyAverage;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getCountSteps() {
        return countSteps;
    }

    public void setCountSteps(Integer countSteps) {
        this.countSteps = countSteps;
    }

    public Integer getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(Integer totalSteps) {
        this.totalSteps = totalSteps;
    }

    public Object getDailyAverage() {
        return dailyAverage;
    }

    public void setDailyAverage(Object dailyAverage) {
        this.dailyAverage = dailyAverage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
