package com.app.furoapp.model.loginUser;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

@SerializedName("email")
@Expose
private String email;
@SerializedName("password")
@Expose
private String password;
@SerializedName("fcm_token")
@Expose
private String fcm_Token;

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getFcmToken() {
return fcm_Token;
}

public void setFcmToken(String fcmToken) {
this.fcm_Token = fcmToken;
}

}