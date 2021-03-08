package com.app.furoapp.model.report;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportIssueRequest {

    @SerializedName("report")
    @Expose
    private String report;
    @SerializedName("user_id")
    @Expose
    private String userId;


    @SerializedName("challenge_id")
    @Expose
    private String challengeid;

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChallengeid() {
        return challengeid;
    }

    public void setChallengeid(String challengeid) {
        this.challengeid = challengeid;
    }


}