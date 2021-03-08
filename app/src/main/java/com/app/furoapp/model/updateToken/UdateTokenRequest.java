package com.app.furoapp.model.updateToken;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UdateTokenRequest {

@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("device_id")
@Expose
private String deviceId;
@SerializedName("fcm_token")
@Expose
private String fcmToken;

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getDeviceId() {
return deviceId;
}

public void setDeviceId(String deviceId) {
this.deviceId = deviceId;
}

public String getFcmToken() {
return fcmToken;
}

public void setFcmToken(String fcmToken) {
this.fcmToken = fcmToken;
}

}