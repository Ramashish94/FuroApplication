
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.dailyWaterIntake;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyWaterIntakeResponse {

    @SerializedName("waterIntakeMonitor")
    @Expose
    private WaterIntakeMonitor waterIntakeMonitor;
    @SerializedName("status")
    @Expose
    private String status;

    public WaterIntakeMonitor getWaterIntakeMonitor() {
        return waterIntakeMonitor;
    }

    public void setWaterIntakeMonitor(WaterIntakeMonitor waterIntakeMonitor) {
        this.waterIntakeMonitor = waterIntakeMonitor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
