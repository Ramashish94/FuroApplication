package com.app.furoapp.activity.newFeature.ContentEngagementModel.like;

import com.google.gson.annotations.SerializedName;

public class LikeResponse{

	@SerializedName("like")
	private Like like;

	@SerializedName("status")
	private String status;

	public void setLike(Like like){
		this.like = like;
	}

	public Like getLike(){
		return like;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}