
package com.app.furoapp.activity.newFeature.notification.allNotificationModal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationResponses {

    @SerializedName("dailyFeedNotification")
    @Expose
    private DailyFeedNotification dailyFeedNotification;
    @SerializedName("challengeNotification")
    @Expose
    private ChallengeNotification challengeNotification;
    @SerializedName("cronNotification")
    @Expose
    private CronNotification cronNotification;
    @SerializedName("status")
    @Expose
    private String status;

    public DailyFeedNotification getDailyFeedNotification() {
        return dailyFeedNotification;
    }

    public void setDailyFeedNotification(DailyFeedNotification dailyFeedNotification) {
        this.dailyFeedNotification = dailyFeedNotification;
    }

    public ChallengeNotification getChallengeNotification() {
        return challengeNotification;
    }

    public void setChallengeNotification(ChallengeNotification challengeNotification) {
        this.challengeNotification = challengeNotification;
    }

    public CronNotification getCronNotification() {
        return cronNotification;
    }

    public void setCronNotification(CronNotification cronNotification) {
        this.cronNotification = cronNotification;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
