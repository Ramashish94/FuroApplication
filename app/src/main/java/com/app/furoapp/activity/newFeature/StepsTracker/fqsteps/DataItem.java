package com.app.furoapp.activity.newFeature.StepsTracker.fqsteps;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("paragraph")
	private String paragraph;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	public void setParagraph(String paragraph){
		this.paragraph = paragraph;
	}

	public String getParagraph(){
		return paragraph;
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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}