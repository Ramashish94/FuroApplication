package com.app.furoapp.model.communityChallange;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommunityChallangeRequest {

@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("community_id")
@Expose
private String communityId;

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getCommunityId() {
return communityId;
}

public void setCommunityId(String communityId) {
this.communityId = communityId;
}

}