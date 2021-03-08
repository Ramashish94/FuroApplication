package com.app.furoapp.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllTime {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("activity_count")
    @Expose
    private String activityCount;
    @SerializedName("acitivity_duration")
    @Expose
    private String acitivityDuration;
    @SerializedName("challenge_activity")
    @Expose
    private String challengeActivity;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("blue_icon")
    @Expose
    private String blueIcon;
    @SerializedName("black_icon")
    @Expose
    private String blackIcon;
    @SerializedName("white_icon")
    @Expose
    private String whiteIcon;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(String activityCount) {
        this.activityCount = activityCount;
    }

    public String getAcitivityDuration() {
        return acitivityDuration;
    }

    public void setAcitivityDuration(String acitivityDuration) {
        this.acitivityDuration = acitivityDuration;
    }

    public String getChallengeActivity() {
        return challengeActivity;
    }

    public void setChallengeActivity(String challengeActivity) {
        this.challengeActivity = challengeActivity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlueIcon() {
        return blueIcon;
    }

    public void setBlueIcon(String blueIcon) {
        this.blueIcon = blueIcon;
    }

    public String getBlackIcon() {
        return blackIcon;
    }

    public void setBlackIcon(String blackIcon) {
        this.blackIcon = blackIcon;
    }

    public String getWhiteIcon() {
        return whiteIcon;
    }

    public void setWhiteIcon(String whiteIcon) {
        this.whiteIcon = whiteIcon;
    }
}
