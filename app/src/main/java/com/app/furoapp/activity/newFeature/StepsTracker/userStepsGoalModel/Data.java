
package com.app.furoapp.activity.newFeature.StepsTracker.userStepsGoalModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("Weekly Counter")
    @Expose
    private String weeklyCounter;
    @SerializedName("Monthly Counter")
    @Expose
    private String monthlyCounter;
    @SerializedName("All Time Counter")
    @Expose
    private String allTimeCounter;
    @SerializedName("Current Time Slot")
    @Expose
    private CurrentTimeSlot currentTimeSlot;

    public String getWeeklyCounter() {
        return weeklyCounter;
    }

    public void setWeeklyCounter(String weeklyCounter) {
        this.weeklyCounter = weeklyCounter;
    }

    public String getMonthlyCounter() {
        return monthlyCounter;
    }

    public void setMonthlyCounter(String monthlyCounter) {
        this.monthlyCounter = monthlyCounter;
    }

    public String getAllTimeCounter() {
        return allTimeCounter;
    }

    public void setAllTimeCounter(String allTimeCounter) {
        this.allTimeCounter = allTimeCounter;
    }

    public CurrentTimeSlot getCurrentTimeSlot() {
        return currentTimeSlot;
    }

    public void setCurrentTimeSlot(CurrentTimeSlot currentTimeSlot) {
        this.currentTimeSlot = currentTimeSlot;
    }

}
