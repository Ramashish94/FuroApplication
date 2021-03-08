package com.app.furoapp.model.profile;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("allTime")
    @Expose
    private List<AllTime> allTime = null;
    @SerializedName("clubs_allTime")
    @Expose
    private List<ClubsAllTime> clubsAllTime = null;
    @SerializedName("weekly")
    @Expose
    private List<Weekly> weekly = null;
    @SerializedName("clubs_weekly")
    @Expose
    private List<ClubsWeekly> clubsWeekly = null;
    @SerializedName("monthly")
    @Expose
    private List<Monthly> monthly = null;
    @SerializedName("clubs_monthly")
    @Expose
    private List<ClubsMonthly> clubsMonthly = null;

    @SerializedName("graph_monthly")
    @Expose
    private List<GraphProfile> graph_monthly = null;

    @SerializedName("graph_weekly")
    @Expose
    private List<GraphProfile> graph_weekly = null;

    @SerializedName("graph_alltime")
    @Expose
    private List<GraphProfile> graph_alltime = null;




    public List<AllTime> getAllTime() {
        return allTime;
    }

    public void setAllTime(List<AllTime> allTime) {
        this.allTime = allTime;
    }

    public List<ClubsAllTime> getClubsAllTime() {
        return clubsAllTime;
    }

    public void setClubsAllTime(List<ClubsAllTime> clubsAllTime) {
        this.clubsAllTime = clubsAllTime;
    }

    public List<Weekly> getWeekly() {
        return weekly;
    }

    public void setWeekly(List<Weekly> weekly) {
        this.weekly = weekly;
    }

    public List<ClubsWeekly> getClubsWeekly() {
        return clubsWeekly;
    }

    public void setClubsWeekly(List<ClubsWeekly> clubsWeekly) {
        this.clubsWeekly = clubsWeekly;
    }

    public List<Monthly> getMonthly() {
        return monthly;
    }

    public void setMonthly(List<Monthly> monthly) {
        this.monthly = monthly;
    }

    public List<ClubsMonthly> getClubsMonthly() {
        return clubsMonthly;
    }

    public void setClubsMonthly(List<ClubsMonthly> clubsMonthly) {
        this.clubsMonthly = clubsMonthly;
    }

    public List<GraphProfile> getGraph_monthly() {
        return graph_monthly;
    }

    public void setGraph_monthly(List<GraphProfile> graph_monthly) {
        this.graph_monthly = graph_monthly;
    }

    public List<GraphProfile> getGraph_weekly() {
        return graph_weekly;
    }

    public void setGraph_weekly(List<GraphProfile> graph_weekly) {
        this.graph_weekly = graph_weekly;
    }

    public List<GraphProfile> getGraph_alltime() {
        return graph_alltime;
    }

    public void setGraph_alltime(List<GraphProfile> graph_alltime) {
        this.graph_alltime = graph_alltime;
    }
}
