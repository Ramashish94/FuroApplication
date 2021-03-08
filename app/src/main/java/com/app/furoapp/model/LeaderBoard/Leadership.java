
package com.app.furoapp.model.LeaderBoard;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Leadership {

    @SerializedName("allTime")
    @Expose
    private List<AllTime> allTime = null;
    @SerializedName("weekly")
    @Expose
    private List<Weekly> weekly = null;
    @SerializedName("monthly")
    @Expose
    private List<Monthly> monthly = null;

    public List<AllTime> getAllTime() {
        return allTime;
    }

    public void setAllTime(List<AllTime> allTime) {
        this.allTime = allTime;
    }

    public List<Weekly> getWeekly() {
        return weekly;
    }

    public void setWeekly(List<Weekly> weekly) {
        this.weekly = weekly;
    }

    public List<Monthly> getMonthly() {
        return monthly;
    }

    public void setMonthly(List<Monthly> monthly) {
        this.monthly = monthly;
    }

}
