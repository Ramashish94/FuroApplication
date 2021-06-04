package com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter;

import com.google.gson.annotations.SerializedName;

public class WaterIntakeUpdatePlanRequest {

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    @SerializedName("plan_id")
    private String plan_id;




}