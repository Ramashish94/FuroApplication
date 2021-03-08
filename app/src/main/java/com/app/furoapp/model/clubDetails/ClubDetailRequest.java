package com.app.furoapp.model.clubDetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClubDetailRequest {

@SerializedName("club_id")
@Expose
private String clubId;

public String getClubId() {
return clubId;
}

public void setClubId(String clubId) {
this.clubId = clubId;
}

}