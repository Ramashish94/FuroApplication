package com.app.furoapp.activity.newFeature.waterIntakeCalculator.dailyWaterIntake;

import com.google.gson.annotations.SerializedName;

public class DailyWaterIntakeRequest {

    @SerializedName("weight")
    private String weight;
    @SerializedName("wake_up_time")
    private String wake_up_time;
    @SerializedName("bed_time")
    private String bed_time;
    @SerializedName("excercise_time")
    private String excercise_time;
    @SerializedName("glass_size_in_ml")
    private String glass_size_in_ml;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWake_up_time() {
        return wake_up_time;
    }

    public void setWake_up_time(String wake_up_time) {
        this.wake_up_time = wake_up_time;
    }

    public String getBed_time() {
        return bed_time;
    }

    public void setBed_time(String bed_time) {
        this.bed_time = bed_time;
    }

    public String getExcercise_time() {
        return excercise_time;
    }

    public void setExcercise_time(String excercise_time) {
        this.excercise_time = excercise_time;
    }

    public String getGlass_size_in_ml() {
        return glass_size_in_ml;
    }

    public void setGlass_size_in_ml(String glass_size_in_ml) {
        this.glass_size_in_ml = glass_size_in_ml;
    }






}