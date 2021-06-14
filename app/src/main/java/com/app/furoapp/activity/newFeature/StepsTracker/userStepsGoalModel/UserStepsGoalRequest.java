package com.app.furoapp.activity.newFeature.StepsTracker.userStepsGoalModel;

import com.google.gson.annotations.SerializedName;

public class UserStepsGoalRequest {

    @SerializedName("time")
    private String time;
    @SerializedName("distance")
    private String distance;
    @SerializedName("calories")
    private String calories;
    @SerializedName("count_steps")
    private String count_steps;
    @SerializedName("total_steps")
    private String total_steps;

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

    public String getCount_steps() {
        return count_steps;
    }

    public void setCount_steps(String count_steps) {
        this.count_steps = count_steps;
    }

    public String getTotal_steps() {
        return total_steps;
    }

    public void setTotal_steps(String total_steps) {
        this.total_steps = total_steps;
    }



}