
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestorePlanResponse {

    @SerializedName("CurrentPlan")
    @Expose
    private CurrentPlan currentPlan;
    @SerializedName("restoredPlan")
    @Expose
    private List<RestoredPlan> restoredPlan = null;
    @SerializedName("weeklyData")
    @Expose
    private WeeklyData weeklyData;
    @SerializedName("monthlyData")
    @Expose
    private MonthlyData monthlyData;
    @SerializedName("allTimeData")
    @Expose
    private AllTimeData allTimeData;
    @SerializedName("status")
    @Expose
    private String status;

    public CurrentPlan getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(CurrentPlan currentPlan) {
        this.currentPlan = currentPlan;
    }

    public List<RestoredPlan> getRestoredPlan() {
        return restoredPlan;
    }

    public void setRestoredPlan(List<RestoredPlan> restoredPlan) {
        this.restoredPlan = restoredPlan;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
