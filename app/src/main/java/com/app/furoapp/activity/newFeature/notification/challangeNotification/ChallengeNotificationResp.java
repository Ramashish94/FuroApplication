
package com.app.furoapp.activity.newFeature.notification.challangeNotification;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallengeNotificationResp {

    @SerializedName("challengeNotification")
    @Expose
    private List<ChallengeNotification> challengeNotification = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<ChallengeNotification> getChallengeNotification() {
        return challengeNotification;
    }

    public void setChallengeNotification(List<ChallengeNotification> challengeNotification) {
        this.challengeNotification = challengeNotification;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
