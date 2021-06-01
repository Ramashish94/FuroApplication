package com.app.furoapp.activity.newFeature.waterIntakeCalculator.planCreate;

import com.google.gson.annotations.SerializedName;

public class PlaneCreateRequest {
    @SerializedName("water_take_in_ml")
    private String water_take_in_ml;

    public String getWater_take_in_ml() {
        return water_take_in_ml;
    }

    public void setWater_take_in_ml(String water_take_in_ml) {
        this.water_take_in_ml = water_take_in_ml;
    }








}