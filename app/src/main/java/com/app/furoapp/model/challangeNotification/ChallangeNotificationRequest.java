package com.app.furoapp.model.challangeNotification;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallangeNotificationRequest {

    @SerializedName("user_id")
    @Expose
    private List<String> userId = null;
    @SerializedName("login_id")
    @Expose
    private String loginId;
    @SerializedName("challenge_id")
    @Expose
    private String challengeId;

    public List<String> getUserId() {
        return userId;
    }

    public void setUserId(List<String> userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }
}