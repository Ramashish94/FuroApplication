package com.app.furoapp.model.challengeAccepted;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallengeAcceptedResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}