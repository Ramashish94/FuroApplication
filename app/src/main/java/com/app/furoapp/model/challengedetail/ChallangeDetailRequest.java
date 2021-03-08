package com.app.furoapp.model.challengedetail;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallangeDetailRequest {

@SerializedName("challenge_id")
@Expose
private String challengeId;

public String getChallengeId() {
return challengeId;
}

public void setChallengeId(String challengeId) {
this.challengeId = challengeId;
}

}