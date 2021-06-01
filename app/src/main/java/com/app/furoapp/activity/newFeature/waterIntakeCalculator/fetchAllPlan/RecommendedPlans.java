
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecommendedPlans {

    @SerializedName("recommendedWater")
    @Expose
    private Integer recommendedWater;
    @SerializedName("timeDuration")
    @Expose
    private Integer timeDuration;

    public Integer getRecommendedWater() {
        return recommendedWater;
    }

    public void setRecommendedWater(Integer recommendedWater) {
        this.recommendedWater = recommendedWater;
    }

    public Integer getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(Integer timeDuration) {
        this.timeDuration = timeDuration;
    }

}
