
package com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.weeklyResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("currentWeekStepCounter")
    @Expose
    private List<CurrentWeekStepCounter> currentWeekStepCounter = null;
    @SerializedName("Weekly Data")
    @Expose
    private WeeklyData weeklyData;

    public List<CurrentWeekStepCounter> getCurrentWeekStepCounter() {
        return currentWeekStepCounter;
    }

    public void setCurrentWeekStepCounter(List<CurrentWeekStepCounter> currentWeekStepCounter) {
        this.currentWeekStepCounter = currentWeekStepCounter;
    }

    public WeeklyData getWeeklyData() {
        return weeklyData;
    }

    public void setWeeklyData(WeeklyData weeklyData) {
        this.weeklyData = weeklyData;
    }

}
