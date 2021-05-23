package com.app.furoapp.activity.newFeature.newFeatureModelByM.activityDetailsNew;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivityDetailResponse{

	@SerializedName("activity_details")
	private List<ActivityDetailsItem> activityDetails;

	@SerializedName("status")
	private String status;

	public void setActivityDetails(List<ActivityDetailsItem> activityDetails){
		this.activityDetails = activityDetails;
	}

	public List<ActivityDetailsItem> getActivityDetails(){
		return activityDetails;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}