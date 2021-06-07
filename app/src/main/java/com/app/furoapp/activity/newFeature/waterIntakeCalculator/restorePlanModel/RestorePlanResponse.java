
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
    @SerializedName("weeklyTakenWaterInMl")
    @Expose
    private Integer weeklyTakenWaterInMl;
    @SerializedName("weeklyTakenGlassOfWater")
    @Expose
    private Integer weeklyTakenGlassOfWater;
    @SerializedName("weeklyRecommendedGlassOfWater")
    @Expose
    private Integer weeklyRecommendedGlassOfWater;
    @SerializedName("monthlyTakenWaterInMl")
    @Expose
    private Integer monthlyTakenWaterInMl;
    @SerializedName("monthlyTakenGlassOfWater")
    @Expose
    private Integer monthlyTakenGlassOfWater;
    @SerializedName("monthlyRecommendedGlassOfWater")
    @Expose
    private Integer monthlyRecommendedGlassOfWater;
    @SerializedName("allTimeTakenWaterInMl")
    @Expose
    private Integer allTimeTakenWaterInMl;
    @SerializedName("allTimeTakenGlassOfWater")
    @Expose
    private String allTimeTakenGlassOfWater;
    @SerializedName("allTimeRecommendedGlassOfWater")
    @Expose
    private String allTimeRecommendedGlassOfWater;
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

    public Integer getWeeklyTakenWaterInMl() {
        return weeklyTakenWaterInMl;
    }

    public void setWeeklyTakenWaterInMl(Integer weeklyTakenWaterInMl) {
        this.weeklyTakenWaterInMl = weeklyTakenWaterInMl;
    }

    public Integer getWeeklyTakenGlassOfWater() {
        return weeklyTakenGlassOfWater;
    }

    public void setWeeklyTakenGlassOfWater(Integer weeklyTakenGlassOfWater) {
        this.weeklyTakenGlassOfWater = weeklyTakenGlassOfWater;
    }

    public Integer getWeeklyRecommendedGlassOfWater() {
        return weeklyRecommendedGlassOfWater;
    }

    public void setWeeklyRecommendedGlassOfWater(Integer weeklyRecommendedGlassOfWater) {
        this.weeklyRecommendedGlassOfWater = weeklyRecommendedGlassOfWater;
    }

    public Integer getMonthlyTakenWaterInMl() {
        return monthlyTakenWaterInMl;
    }

    public void setMonthlyTakenWaterInMl(Integer monthlyTakenWaterInMl) {
        this.monthlyTakenWaterInMl = monthlyTakenWaterInMl;
    }

    public Integer getMonthlyTakenGlassOfWater() {
        return monthlyTakenGlassOfWater;
    }

    public void setMonthlyTakenGlassOfWater(Integer monthlyTakenGlassOfWater) {
        this.monthlyTakenGlassOfWater = monthlyTakenGlassOfWater;
    }

    public Integer getMonthlyRecommendedGlassOfWater() {
        return monthlyRecommendedGlassOfWater;
    }

    public void setMonthlyRecommendedGlassOfWater(Integer monthlyRecommendedGlassOfWater) {
        this.monthlyRecommendedGlassOfWater = monthlyRecommendedGlassOfWater;
    }

    public Integer getAllTimeTakenWaterInMl() {
        return allTimeTakenWaterInMl;
    }

    public void setAllTimeTakenWaterInMl(Integer allTimeTakenWaterInMl) {
        this.allTimeTakenWaterInMl = allTimeTakenWaterInMl;
    }

    public String getAllTimeTakenGlassOfWater() {
        return allTimeTakenGlassOfWater;
    }

    public void setAllTimeTakenGlassOfWater(String allTimeTakenGlassOfWater) {
        this.allTimeTakenGlassOfWater = allTimeTakenGlassOfWater;
    }

    public String getAllTimeRecommendedGlassOfWater() {
        return allTimeRecommendedGlassOfWater;
    }

    public void setAllTimeRecommendedGlassOfWater(String allTimeRecommendedGlassOfWater) {
        this.allTimeRecommendedGlassOfWater = allTimeRecommendedGlassOfWater;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
