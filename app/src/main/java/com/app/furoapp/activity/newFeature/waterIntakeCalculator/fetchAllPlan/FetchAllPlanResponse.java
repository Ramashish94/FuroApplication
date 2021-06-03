
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchAllPlanResponse {

    @SerializedName("glassSize")
    @Expose
    private GlassSize glassSize;
    @SerializedName("allPlans")
    @Expose
    private List<AllPlan> allPlans = null;
    @SerializedName("status")
    @Expose
    private String status;

    public GlassSize getGlassSize() {
        return glassSize;
    }

    public void setGlassSize(GlassSize glassSize) {
        this.glassSize = glassSize;
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
