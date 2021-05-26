
package com.app.furoapp.activity.newFeature.bmiCalculator.bmiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BmiStoreDataResponse {

    @SerializedName("BMI")
    @Expose
    private Bmi bmi;
    @SerializedName("status")
    @Expose
    private String status;

    public Bmi getBmi() {
        return bmi;
    }

    public void setBmi(Bmi bmi) {
        this.bmi = bmi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
