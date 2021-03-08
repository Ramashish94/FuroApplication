package com.app.furoapp.model.emailVerified;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailVerifiedResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("otp")
@Expose
private Integer otp;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public Integer getOtp() {
return otp;
}

public void setOtp(Integer otp) {
this.otp = otp;
}

}