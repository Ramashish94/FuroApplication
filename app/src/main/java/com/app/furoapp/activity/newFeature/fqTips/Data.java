
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

}
