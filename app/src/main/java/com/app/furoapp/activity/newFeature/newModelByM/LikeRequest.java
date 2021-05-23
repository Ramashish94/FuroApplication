package com.app.furoapp.activity.newFeature.newModelByM;

import com.google.gson.annotations.SerializedName;

public class LikeRequest{

	@SerializedName("post_id")
	private int postId;

	@SerializedName("like_flag")
	private int likeFlag;

	public void setPostId(int postId){
		this.postId = postId;
	}

	public int getPostId(){
		return postId;
	}

	public void setLikeFlag(int likeFlag){
		this.likeFlag = likeFlag;
	}

	public int getLikeFlag(){
		return likeFlag;
	}
}