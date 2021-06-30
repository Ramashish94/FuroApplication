package com.app.furoapp.activity.newFeature.StepsTracker.fqsteps;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("Data")
	private List<DataItem> data;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}
}