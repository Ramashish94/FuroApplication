package com.app.furoapp.activity.newFeature.ContentEngagementModel.addComments;

import com.google.gson.annotations.SerializedName;

public class AddCommentRequest{

	@SerializedName("post_id")
	private int postId;

	@SerializedName("comment")
	private String comment;

	public void setPostId(int postId){
		this.postId = postId;
	}

	public int getPostId(){
		return postId;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return comment;
	}
}