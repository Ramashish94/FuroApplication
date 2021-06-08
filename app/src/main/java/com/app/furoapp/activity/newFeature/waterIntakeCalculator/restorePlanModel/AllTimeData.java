
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllTimeData {

    @SerializedName("allTimeTakenGlassOfWater")
    @Expose
    private Integer allTimeTakenGlassOfWater;
    @SerializedName("allTimeRecommendedGlassOfWater")
    @Expose
    private Integer allTimeRecommendedGlassOfWater;
    @SerializedName("allTimeTakenWaterInMl")
    @Expose
    private Integer allTimeTakenWaterInMl;

    public Integer getAllTimeTakenGlassOfWater() {
        return allTimeTakenGlassOfWater;
    }

    public void setAllTimeTakenGlassOfWater(Integer allTimeTakenGlassOfWater) {
        this.allTimeTakenGlassOfWater = allTimeTakenGlassOfWater;
    }

    public Integer getAllTimeRecommendedGlassOfWater() {
        return allTimeRecommendedGlassOfWater;
    }

    public void setAllTimeRecommendedGlassOfWater(Integer allTimeRecommendedGlassOfWater) {
        this.allTimeRecommendedGlassOfWater = allTimeRecommendedGlassOfWater;
    }

    public Integer getAllTimeTakenWaterInMl() {
        return allTimeTakenWaterInMl;
    }

    public void setAllTimeTakenWaterInMl(Integer allTimeTakenWaterInMl) {
        this.allTimeTakenWaterInMl = allTimeTakenWaterInMl;
    }

}
