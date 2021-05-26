package com.app.furoapp.activity.newFeature.newFeatureModelByM.activityDetailsNew;

import com.google.gson.annotations.SerializedName;

public class CommentsItem{

	@SerializedName("child_id")
	private Object childId;

	@SerializedName("post_id")
	private int postId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("comment")
	private String comment;

	@SerializedName("id")
	private int id;

	@SerializedName("reply")
	private Object reply;

	@SerializedName("status")
	private String status;

	public void setChildId(Object childId){
		this.childId = childId;
	}

	public Object getChildId(){
		return childId;
	}

	public void setPostId(int postId){
		this.postId = postId;
	}

	public int getPostId(){
		return postId;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return comment;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setReply(Object reply){
		this.reply = reply;
	}

	public Object getReply(){
		return reply;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}