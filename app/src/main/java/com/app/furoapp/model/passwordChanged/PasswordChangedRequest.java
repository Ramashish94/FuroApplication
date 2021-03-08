package com.app.furoapp.model.passwordChanged;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PasswordChangedRequest {

@SerializedName("email")
@Expose
private String email;
@SerializedName("new_password")
@Expose
private String newPassword;
@SerializedName("otp")
@Expose
private String otp;

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getNewPassword() {
return newPassword;
}

public void setNewPassword(String newPassword) {
this.newPassword = newPassword;
}

public String getOtp() {
return otp;
}

public void setOtp(String otp) {
this.otp = otp;
}

}