
package com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.yearResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("yearlyDataLists")
    @Expose
    private List<YearlyDataList> yearlyDataLists = null;
    @SerializedName("yearlyData")
    @Expose
    private YearlyData yearlyData;

    public List<YearlyDataList> getYearlyDataLists() {
        return yearlyDataLists;
    }

    public void setYearlyDataLists(List<YearlyDataList> yearlyDataLists) {
        this.yearlyDataLists = yearlyDataLists;
    }

    public YearlyData getYearlyData() {
        return yearlyData;
    }

    public void setYearlyData(YearlyData yearlyData) {
        this.yearlyData = yearlyData;
    }

}
