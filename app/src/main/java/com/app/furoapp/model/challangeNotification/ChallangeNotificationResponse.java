package com.app.furoapp.model.challangeNotification;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallangeNotificationResponse {

@SerializedName("status")
@Expose
private String status;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

}