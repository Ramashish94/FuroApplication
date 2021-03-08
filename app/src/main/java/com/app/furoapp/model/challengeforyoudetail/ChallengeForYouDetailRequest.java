package com.app.furoapp.model.challengeforyoudetail;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallengeForYouDetailRequest {

@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("challenge_id")
@Expose
private String challengeId;

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getChallengeId() {
return challengeId;
}

public void setChallengeId(String challengeId) {
this.challengeId = challengeId;
}

}