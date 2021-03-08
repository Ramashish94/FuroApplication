package com.app.furoapp.model.mapchallenge;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MapChallengeRecieveRequest {

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