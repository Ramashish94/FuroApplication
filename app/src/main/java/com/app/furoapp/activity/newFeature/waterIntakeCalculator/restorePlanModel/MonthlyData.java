
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonthlyData {

    @SerializedName("monthlyTakenGlassOfWater")
    @Expose
    private Integer monthlyTakenGlassOfWater;
    @SerializedName("monthlyRecommendedGlassOfWater")
    @Expose
    private Integer monthlyRecommendedGlassOfWater;
    @SerializedName("monthlyTakenWaterInMl")
    @Expose
    private Integer monthlyTakenWaterInMl;

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

    public Integer getMonthlyTakenWaterInMl() {
        return monthlyTakenWaterInMl;
    }

    public void setMonthlyTakenWaterInMl(Integer monthlyTakenWaterInMl) {
        this.monthlyTakenWaterInMl = monthlyTakenWaterInMl;
    }

}
