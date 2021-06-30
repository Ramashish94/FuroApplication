
package com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user_detail")
    @Expose
    private UserDetail userDetail;
    @SerializedName("daily_data_count")
    @Expose
    private List<DailyDataCount> dailyDataCount = null;
    @SerializedName("weekly_data_count")
    @Expose
    private List<WeeklyDataCount> weeklyDataCount = null;
    @SerializedName("monthly_data_count")
    @Expose
    private List<MonthlyDataCount> monthlyDataCount = null;

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public List<DailyDataCount> getDailyDataCount() {
        return dailyDataCount;
    }

    public void setDailyDataCount(List<DailyDataCount> dailyDataCount) {
        this.dailyDataCount = dailyDataCount;
    }

    public List<WeeklyDataCount> getWeeklyDataCount() {
        return weeklyDataCount;
    }

    public void setWeeklyDataCount(List<WeeklyDataCount> weeklyDataCount) {
        this.weeklyDataCount = weeklyDataCount;
    }

    public List<MonthlyDataCount> getMonthlyDataCount() {
        return monthlyDataCount;
    }

    public void setMonthlyDataCount(List<MonthlyDataCount> monthlyDataCount) {
        this.monthlyDataCount = monthlyDataCount;
    }

}
