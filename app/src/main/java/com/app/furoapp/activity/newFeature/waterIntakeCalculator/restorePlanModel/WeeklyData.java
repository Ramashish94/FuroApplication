
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeeklyData {

    @SerializedName("weeklyTakenGlassOfWater")
    @Expose
    private Integer weeklyTakenGlassOfWater;
    @SerializedName("weeklyRecommendedGlassOfWater")
    @Expose
    private Integer weeklyRecommendedGlassOfWater;
    @SerializedName("weeklyTakenWaterInMl")
    @Expose
    private Integer weeklyTakenWaterInMl;

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

    public Integer getWeeklyTakenWaterInMl() {
        return weeklyTakenWaterInMl;
    }

    public void setWeeklyTakenWaterInMl(Integer weeklyTakenWaterInMl) {
        this.weeklyTakenWaterInMl = weeklyTakenWaterInMl;
    }

}
