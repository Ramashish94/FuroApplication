package com.app.furoapp.activity.newFeature.newFeatureModelByM.saveBookmark;

import com.google.gson.annotations.SerializedName;

public class SavedRequest {

	@SerializedName("post_id")
	private int postId;

	@SerializedName("save_flag")
	private int saveFlag;

	public void setPostId(int postId){
		this.postId = postId;
	}

	public int getPostId(){
		return postId;
	}

	public int getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(int saveFlag) {
		this.saveFlag = saveFlag;
	}
}