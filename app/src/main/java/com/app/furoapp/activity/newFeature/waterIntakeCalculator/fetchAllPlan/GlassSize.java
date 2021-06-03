
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlassSize {

    @SerializedName("glass_size_in_ml")
    @Expose
    private String glassSizeInMl;

    public String getGlassSizeInMl() {
        return glassSizeInMl;
    }

    public void setGlassSizeInMl(String glassSizeInMl) {
        this.glassSizeInMl = glassSizeInMl;
    }

}
