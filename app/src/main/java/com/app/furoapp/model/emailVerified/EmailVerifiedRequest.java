package com.app.furoapp.model.emailVerified;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailVerifiedRequest {

@SerializedName("email")
@Expose
private String email;

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

}