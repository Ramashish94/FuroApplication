
package com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.monthlyResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("MonthStepCounter")
    @Expose
    private List<MonthStepCounter> monthStepCounter = null;
    @SerializedName("monthlyData")
    @Expose
    private MonthlyData monthlyData;

    public List<MonthStepCounter> getMonthStepCounter() {
        return monthStepCounter;
    }

    public void setMonthStepCounter(List<MonthStepCounter> monthStepCounter) {
        this.monthStepCounter = monthStepCounter;
    }

    public MonthlyData getMonthlyData() {
        return monthlyData;
    }

    public void setMonthlyData(MonthlyData monthlyData) {
        this.monthlyData = monthlyData;
    }

}
