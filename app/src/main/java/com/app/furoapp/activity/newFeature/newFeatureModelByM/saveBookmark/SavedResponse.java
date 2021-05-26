package com.app.furoapp.activity.newFeature.newFeatureModelByM.saveBookmark;

import com.google.gson.annotations.SerializedName;

public class SavedResponse{

	@SerializedName("Saved")
	private Saved saved;

	@SerializedName("status")
	private String status;

	public void setSaved(Saved saved){
		this.saved = saved;
	}

	public Saved getSaved(){
		return saved;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}