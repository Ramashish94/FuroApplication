package com.app.furoapp.model.whatBringsYoutoFuro;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WhatBringsYouToFuroRequest {

@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("weight")
@Expose
private String weight;
@SerializedName("height")
@Expose
private String height;
@SerializedName("reasons")
@Expose
private String reasons;

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getWeight() {
return weight;
}

public void setWeight(String weight) {
this.weight = weight;
}

public String getHeight() {
return height;
}

public void setHeight(String height) {
this.height = height;
}

public String getReasons() {
return reasons;
}

public void setReasons(String reasons) {
this.reasons = reasons;
}

}