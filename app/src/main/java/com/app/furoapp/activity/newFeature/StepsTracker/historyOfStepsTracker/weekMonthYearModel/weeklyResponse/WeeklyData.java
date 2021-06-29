
package com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.weeklyResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeeklyData {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("calories")
    @Expose
    private String calories;
    @SerializedName("count_steps")
    @Expose
    private String countSteps;
    @SerializedName("total_steps")
    @Expose
    private String totalSteps;
    @SerializedName("daily_average")
    @Expose
    private Integer dailyAverage;
    @SerializedName("currentDate")
    @Expose
    private String currentDate;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public Integer getDailyAverage() {
        return dailyAverage;
    }

    public void setDailyAverage(Integer dailyAverage) {
        this.dailyAverage = dailyAverage;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

}
