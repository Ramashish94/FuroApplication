
package com.app.furoapp.activity.newFeature.healthCare.healthCentermodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthCenterResponse {

    @SerializedName("stepCounter")
    @Expose
    private StepCounter stepCounter;
    @SerializedName("waterIntake")
    @Expose
    private WaterIntake waterIntake;
    @SerializedName("bmi")
    @Expose
    private Bmi bmi;
    @SerializedName("glasstakentime")
    @Expose
    private String glasstakentime;
    @SerializedName("status")
    @Expose
    private String status;

    public StepCounter getStepCounter() {
        return stepCounter;
    }

    public void setStepCounter(StepCounter stepCounter) {
        this.stepCounter = stepCounter;
    }

    public WaterIntake getWaterIntake() {
        return waterIntake;
    }

    public void setWaterIntake(WaterIntake waterIntake) {
        this.waterIntake = waterIntake;
    }

    public Bmi getBmi() {
        return bmi;
    }

    public void setBmi(Bmi bmi) {
        this.bmi = bmi;
    }

    public String getGlasstakentime() {
        return glasstakentime;
    }

    public void setGlasstakentime(String glasstakentime) {
        this.glasstakentime = glasstakentime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
