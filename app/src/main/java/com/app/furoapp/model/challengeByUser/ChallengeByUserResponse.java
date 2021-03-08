
package com.app.furoapp.model.challengeByUser;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallengeByUserResponse {

    @SerializedName("SendChallenge")
    @Expose
    private List<SendChallenge> sendChallenge = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<SendChallenge> getSendChallenge() {
        return sendChallenge;
    }

    public void setSendChallenge(List<SendChallenge> sendChallenge) {
        this.sendChallenge = sendChallenge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
