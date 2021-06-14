
package com.app.furoapp.activity.newFeature.StepsTracker.historyModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllTimeData {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("calories")
    @Expose
    private String calories;
    @SerializedName("total_steps")
    @Expose
    private String totalSteps;
    @SerializedName("daily_average")
    @Expose
    private Integer dailyAverage;

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

}
