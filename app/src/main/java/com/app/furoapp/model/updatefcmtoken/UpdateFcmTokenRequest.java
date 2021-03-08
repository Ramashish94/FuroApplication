package com.app.furoapp.model.updatefcmtoken;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateFcmTokenRequest {

@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("fcm_token")
@Expose
private String fcmToken;

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getFcmToken() {
return fcmToken;
}

public void setFcmToken(String fcmToken) {
this.fcmToken = fcmToken;
}

}