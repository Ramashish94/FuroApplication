
package com.app.furoapp.activity.newFeature.StepsTracker.historyModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("Weekly Data")
    @Expose
    private WeeklyData weeklyData;
    @SerializedName("Monthly Data")
    @Expose
    private MonthlyData monthlyData;
    @SerializedName("All Time Data")
    @Expose
    private AllTimeData allTimeData;
    @SerializedName("All Time Counter List")
    @Expose
    private List<AllTimeCounter> allTimeCounterList = null;

    public WeeklyData getWeeklyData() {
        return weeklyData;
    }

    public void setWeeklyData(WeeklyData weeklyData) {
        this.weeklyData = weeklyData;
    }

    public MonthlyData getMonthlyData() {
        return monthlyData;
    }

    public void setMonthlyData(MonthlyData monthlyData) {
        this.monthlyData = monthlyData;
    }

    public AllTimeData getAllTimeData() {
        return allTimeData;
    }

    public void setAllTimeData(AllTimeData allTimeData) {
        this.allTimeData = allTimeData;
    }

    public List<AllTimeCounter> getAllTimeCounterList() {
        return allTimeCounterList;
    }

    public void setAllTimeCounterList(List<AllTimeCounter> allTimeCounterList) {
        this.allTimeCounterList = allTimeCounterList;
    }

}
