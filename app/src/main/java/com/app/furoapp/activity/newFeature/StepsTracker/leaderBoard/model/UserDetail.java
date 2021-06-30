
package com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetail {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("daily_count_steps")
    @Expose
    private String dailyCountSteps;
    @SerializedName("weekly_count_steps")
    @Expose
    private String weeklyCountSteps;
    @SerializedName("monthly_count_steps")
    @Expose
    private String monthlyCountSteps;
    @SerializedName("dailyPosition")
    @Expose
    private Integer dailyPosition;
    @SerializedName("weeklyPosition")
    @Expose
    private Integer weeklyPosition;
    @SerializedName("monthlyPosition")
    @Expose
    private Integer monthlyPosition;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getDailyCountSteps() {
        return dailyCountSteps;
    }

    public void setDailyCountSteps(String dailyCountSteps) {
        this.dailyCountSteps = dailyCountSteps;
    }

    public String getWeeklyCountSteps() {
        return weeklyCountSteps;
    }

    public void setWeeklyCountSteps(String weeklyCountSteps) {
        this.weeklyCountSteps = weeklyCountSteps;
    }

    public String getMonthlyCountSteps() {
        return monthlyCountSteps;
    }

    public void setMonthlyCountSteps(String monthlyCountSteps) {
        this.monthlyCountSteps = monthlyCountSteps;
    }

    public Integer getDailyPosition() {
        return dailyPosition;
    }

    public void setDailyPosition(Integer dailyPosition) {
        this.dailyPosition = dailyPosition;
    }

    public Integer getWeeklyPosition() {
        return weeklyPosition;
    }

    public void setWeeklyPosition(Integer weeklyPosition) {
        this.weeklyPosition = weeklyPosition;
    }

    public Integer getMonthlyPosition() {
        return monthlyPosition;
    }

    public void setMonthlyPosition(Integer monthlyPosition) {
        this.monthlyPosition = monthlyPosition;
    }

}
