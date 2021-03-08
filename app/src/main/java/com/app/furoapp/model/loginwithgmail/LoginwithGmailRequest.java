package com.app.furoapp.model.loginwithgmail;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginwithGmailRequest {

@SerializedName("google_id")
@Expose
private String googleId;

public String getGoogleId() {
return googleId;
}

public void setGoogleId(String googleId) {
this.googleId = googleId;
}

}