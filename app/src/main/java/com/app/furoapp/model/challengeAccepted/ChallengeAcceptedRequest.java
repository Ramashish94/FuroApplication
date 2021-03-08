
package com.app.furoapp.model.challengeAccepted;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallengeAcceptedRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("challenge_id")
    @Expose
    private String challengeId;
    @SerializedName("sender_id")
    @Expose
    private String senderId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

}