
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.planCreateModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("recommendedWater")
    @Expose
    private Double recommendedWater;
    @SerializedName("requestedWater")
    @Expose
    private String requestedWater;
    @SerializedName("timeDuration")
    @Expose
    private Integer timeDuration;
    @SerializedName("suggestion")
    @Expose
    private String suggestion;

    public Double getRecommendedWater() {
        return recommendedWater;
    }

    public void setRecommendedWater(Double recommendedWater) {
        this.recommendedWater = recommendedWater;
    }

    public String getRequestedWater() {
        return requestedWater;
    }

    public void setRequestedWater(String requestedWater) {
        this.requestedWater = requestedWater;
    }

    public Integer getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(Integer timeDuration) {
        this.timeDuration = timeDuration;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

}
