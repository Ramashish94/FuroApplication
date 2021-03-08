package com.app.furoapp.model.loginWithFb;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginWithFbRequest {

@SerializedName("fb_id")
@Expose
private String fbId;

public String getFbId() {
return fbId;
}

public void setFbId(String fbId) {
this.fbId = fbId;
}

}