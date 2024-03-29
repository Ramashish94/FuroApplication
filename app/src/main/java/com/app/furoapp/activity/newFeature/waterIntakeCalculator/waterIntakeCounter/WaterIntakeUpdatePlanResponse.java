
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WaterIntakeUpdatePlanResponse {

    @SerializedName("planUpdated")
    @Expose
    private String planUpdated;
    @SerializedName("glass_size")
    @Expose
    private String glassSize;
    @SerializedName("selectedPlan")
    @Expose
    private SelectedPlan selectedPlan;
    @SerializedName("weeklyTakenWaterInMl")
    @Expose
    private Integer weeklyTakenWaterInMl;
    @SerializedName("weeklyTakenGlassOfWater")
    @Expose
    private String weeklyTakenGlassOfWater;
    @SerializedName("weeklyRecommendedGlassOfWater")
    @Expose
    private String weeklyRecommendedGlassOfWater;
    @SerializedName("monthlyTakenWaterInMl")
    @Expose
    private Integer monthlyTakenWaterInMl;
    @SerializedName("monthlyTakenGlassOfWater")
    @Expose
    private String monthlyTakenGlassOfWater;
    @SerializedName("monthlyRecommendedGlassOfWater")
    @Expose
    private String monthlyRecommendedGlassOfWater;
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

    public String getPlanUpdated() {
        return planUpdated;
    }

    public void setPlanUpdated(String planUpdated) {
        this.planUpdated = planUpdated;
    }

    public String getGlassSize() {
        return glassSize;
    }

    public void setGlassSize(String glassSize) {
        this.glassSize = glassSize;
    }

    public SelectedPlan getSelectedPlan() {
        return selectedPlan;
    }

    public void setSelectedPlan(SelectedPlan selectedPlan) {
        this.selectedPlan = selectedPlan;
    }

    public Integer getWeeklyTakenWaterInMl() {
        return weeklyTakenWaterInMl;
    }

    public void setWeeklyTakenWaterInMl(Integer weeklyTakenWaterInMl) {
        this.weeklyTakenWaterInMl = weeklyTakenWaterInMl;
    }

    public String getWeeklyTakenGlassOfWater() {
        return weeklyTakenGlassOfWater;
    }

    public void setWeeklyTakenGlassOfWater(String weeklyTakenGlassOfWater) {
        this.weeklyTakenGlassOfWater = weeklyTakenGlassOfWater;
    }

    public String getWeeklyRecommendedGlassOfWater() {
        return weeklyRecommendedGlassOfWater;
    }

    public void setWeeklyRecommendedGlassOfWater(String weeklyRecommendedGlassOfWater) {
        this.weeklyRecommendedGlassOfWater = weeklyRecommendedGlassOfWater;
    }

    public Integer getMonthlyTakenWaterInMl() {
        return monthlyTakenWaterInMl;
    }

    public void setMonthlyTakenWaterInMl(Integer monthlyTakenWaterInMl) {
        this.monthlyTakenWaterInMl = monthlyTakenWaterInMl;
    }

    public String getMonthlyTakenGlassOfWater() {
        return monthlyTakenGlassOfWater;
    }

    public void setMonthlyTakenGlassOfWater(String monthlyTakenGlassOfWater) {
        this.monthlyTakenGlassOfWater = monthlyTakenGlassOfWater;
    }

    public String getMonthlyRecommendedGlassOfWater() {
        return monthlyRecommendedGlassOfWater;
    }

    public void setMonthlyRecommendedGlassOfWater(String monthlyRecommendedGlassOfWater) {
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
