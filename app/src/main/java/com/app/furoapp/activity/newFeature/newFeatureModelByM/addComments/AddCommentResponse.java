package com.app.furoapp.activity.newFeature.newFeatureModelByM.addComments;

import com.google.gson.annotations.SerializedName;

public class AddCommentResponse{

	@SerializedName("Comment")
	private Comment comment;

	@SerializedName("status")
	private String status;

	public void setComment(Comment comment){
		this.comment = comment;
	}

	public Comment getComment(){
		return comment;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}