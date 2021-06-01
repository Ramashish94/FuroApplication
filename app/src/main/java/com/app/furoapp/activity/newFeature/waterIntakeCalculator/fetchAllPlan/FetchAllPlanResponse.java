
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchAllPlanResponse {

    @SerializedName("recommendedPlans")
    @Expose
    private RecommendedPlans recommendedPlans;
    @SerializedName("allPlans")
    @Expose
    private List<AllPlan> allPlans = null;
    @SerializedName("status")
    @Expose
    private String status;

    public RecommendedPlans getRecommendedPlans() {
        return recommendedPlans;
    }

    public void setRecommendedPlans(RecommendedPlans recommendedPlans) {
        this.recommendedPlans = recommendedPlans;
    }

    public List<AllPlan> getAllPlans() {
        return allPlans;
    }

    public void setAllPlans(List<AllPlan> allPlans) {
        this.allPlans = allPlans;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
