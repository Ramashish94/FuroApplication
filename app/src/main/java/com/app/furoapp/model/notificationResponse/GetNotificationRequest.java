package com.app.furoapp.model.notificationResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNotificationRequest {

@SerializedName("fcm_token")
@Expose
private String fcmToken;
@SerializedName("device_id")
@Expose
private String deviceId;
@SerializedName("user_id")
@Expose
private String userId;

public String getFcmToken() {
return fcmToken;
}

public void setFcmToken(String fcmToken) {
this.fcmToken = fcmToken;
}

public String getDeviceId() {
return deviceId;
}

public void setDeviceId(String deviceId) {
this.deviceId = deviceId;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

}