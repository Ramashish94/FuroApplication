package com.app.furoapp.activity.newFeature.newModelByM;

import com.google.gson.annotations.SerializedName;

public class BodyItem{

	@SerializedName("file_name")
	private String fileName;

	@SerializedName("type")
	private String type;

	@SerializedName("value")
	private String value;

	public void setFileName(String fileName){
		this.fileName = fileName;
	}

	public String getFileName(){
		return fileName;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}
}