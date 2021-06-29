
package com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.yearResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YearlyDataList {

    @SerializedName("time")
    @Expose
    private Integer time;
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
    private Integer dailyAverage;
    @SerializedName("month")
    @Expose
    private String month;
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
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

    public Integer getDailyAverage() {
        return dailyAverage;
    }

    public void setDailyAverage(Integer dailyAverage) {
        this.dailyAverage = dailyAverage;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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
