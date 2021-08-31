
package com.app.furoapp.activity.newFeature.fqTips;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("bmiData")
    @Expose
    private List<BmiDatum> bmiData = null;
    @SerializedName("waterIntakeData")
    @Expose
    private List<WaterIntakeDatum> waterIntakeData = null;
    @SerializedName("stepCounterData")
    @Expose
    private List<StepCounterDatum> stepCounterData = null;

    public List<BmiDatum> getBmiData() {
        return bmiData;
    }

    public void setBmiData(List<BmiDatum> bmiData) {
        this.bmiData = bmiData;
    }

    public List<WaterIntakeDatum> getWaterIntakeData() {
        return waterIntakeData;
    }

    public void setWaterIntakeData(List<WaterIntakeDatum> waterIntakeData) {
        this.waterIntakeData = waterIntakeData;
    }

    public List<StepCounterDatum> getStepCounterData() {
        return stepCounterData;
    }

    public void setStepCounterData(List<StepCounterDatum> stepCounterData) {
        this.stepCounterData = stepCounterData;
    }

}
