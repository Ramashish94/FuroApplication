package com.app.furoapp.activity.newFeature.StepsTracker.modifiedSavedData;

import com.google.gson.annotations.SerializedName;

public class ModifiedSavedDataRequest {

    @SerializedName("gender")
    private String gender;
    @SerializedName("age")
    private String age;
    @SerializedName("height")
    private String height;
    @SerializedName("weight")
    private String weight;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }





}