
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.WaterIntakeExistsUser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WaterIntakeExistsUserResponse {

    @SerializedName("is_water_intake_data_required")
    @Expose
    private Integer isWaterIntakeDataRequired;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getIsWaterIntakeDataRequired() {
        return isWaterIntakeDataRequired;
    }

    public void setIsWaterIntakeDataRequired(Integer isWaterIntakeDataRequired) {
        this.isWaterIntakeDataRequired = isWaterIntakeDataRequired;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
