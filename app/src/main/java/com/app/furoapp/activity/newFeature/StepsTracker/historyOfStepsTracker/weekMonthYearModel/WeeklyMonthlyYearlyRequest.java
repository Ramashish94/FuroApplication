package com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel;

import com.google.gson.annotations.SerializedName;

public class WeeklyMonthlyYearlyRequest {

    @SerializedName("month")
    private String month;
    @SerializedName("year")
    private String year;
    @SerializedName("type")
    private String type;
    @SerializedName("date_range")
    private String date_range;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate_range() {
        return date_range;
    }

    public void setDate_range(String date_range) {
        this.date_range = date_range;
    }


}
