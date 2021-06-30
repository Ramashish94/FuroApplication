
package com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("User ID")
    @Expose
    private Integer userID;
    @SerializedName("Daily Data Count")
    @Expose
    private List<DailyDataCount> dailyDataCount = null;
    @SerializedName("Weekly Data Count")
    @Expose
    private List<WeeklyDataCount> weeklyDataCount = null;
    @SerializedName("Monthly Data Count")
    @Expose
    private List<MonthlyDataCount> monthlyDataCount = null;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public List<DailyDataCount> getDailyDataCount() {
        return dailyDataCount;
    }

    public void setDailyDataCount(List<DailyDataCount> dailyDataCount) {
        this.dailyDataCount = dailyDataCount;
    }

    public List<WeeklyDataCount> getWeeklyDataCount() {
        return weeklyDataCount;
    }

    public void setWeeklyDataCount(List<WeeklyDataCount> weeklyDataCount) {
        this.weeklyDataCount = weeklyDataCount;
    }

    public List<MonthlyDataCount> getMonthlyDataCount() {
        return monthlyDataCount;
    }

    public void setMonthlyDataCount(List<MonthlyDataCount> monthlyDataCount) {
        this.monthlyDataCount = monthlyDataCount;
    }

}
