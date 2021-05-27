
package com.app.furoapp.activity.newFeature.bmiCalculator.fetchBmiDataModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchUserWiseDataResponse {

    @SerializedName("BMI")
    @Expose
    private List<Bmi> bmi = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Bmi> getBmi() {
        return bmi;
    }

    public void setBmi(List<Bmi> bmi) {
        this.bmi = bmi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
