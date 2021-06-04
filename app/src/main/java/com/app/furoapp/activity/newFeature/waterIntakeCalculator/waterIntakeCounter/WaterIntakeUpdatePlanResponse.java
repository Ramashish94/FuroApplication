
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
