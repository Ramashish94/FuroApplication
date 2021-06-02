package com.app.furoapp.activity.newFeature.waterIntakeCalculator.customeSizeGlass;

import com.google.gson.annotations.SerializedName;

public class CustomGlassSizeRequest {


    @SerializedName("glass_size_in_ml")
    private String glass_size_in_ml;

    public String getGlass_size_in_ml() {
        return glass_size_in_ml;
    }

    public void setGlass_size_in_ml(String glass_size_in_ml) {
        this.glass_size_in_ml = glass_size_in_ml;
    }






}