package com.app.furoapp.activity.newFeature.notification;

public class NotificationModelTests {
    String notification;
    String notificationReadTime;

    @Override
    public String toString() {
        return "NotificationModelTests{" +
                "notification='" + notification + '\'' +
                ", notificationReadTime='" + notificationReadTime + '\'' +
                '}';
    }


    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getNotificationReadTime() {
        return notificationReadTime;
    }

    public void setNotificationReadTime(String notificationReadTime) {
        this.notificationReadTime = notificationReadTime;
    }


}
