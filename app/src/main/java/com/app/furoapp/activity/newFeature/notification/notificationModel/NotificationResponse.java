
package com.app.furoapp.activity.newFeature.notification.notificationModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationResponse {

    @SerializedName("dailyFeedNotification")
    @Expose
    private List<DailyFeedNotification> dailyFeedNotification = null;
    @SerializedName("challengeNotification")
    @Expose
    private List<ChallengeNotification> challengeNotification = null;
    @SerializedName("cronNotification")
    @Expose
    private List<CronNotification> cronNotification = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<DailyFeedNotification> getDailyFeedNotification() {
        return dailyFeedNotification;
    }

    public void setDailyFeedNotification(List<DailyFeedNotification> dailyFeedNotification) {
        this.dailyFeedNotification = dailyFeedNotification;
    }

    public List<ChallengeNotification> getChallengeNotification() {
        return challengeNotification;
    }

    public void setChallengeNotification(List<ChallengeNotification> challengeNotification) {
        this.challengeNotification = challengeNotification;
    }

    public List<CronNotification> getCronNotification() {
        return cronNotification;
    }

    public void setCronNotification(List<CronNotification> cronNotification) {
        this.cronNotification = cronNotification;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
