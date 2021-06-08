
package com.app.furoapp.activity.newFeature.notification.dailyNotification;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyFeedNotificationResponse {

    @SerializedName("dailyFeedNotification")
    @Expose
    private List<DailyFeedNotification> dailyFeedNotification = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<DailyFeedNotification> getDailyFeedNotification() {
        return dailyFeedNotification;
    }

    public void setDailyFeedNotification(List<DailyFeedNotification> dailyFeedNotification) {
        this.dailyFeedNotification = dailyFeedNotification;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
