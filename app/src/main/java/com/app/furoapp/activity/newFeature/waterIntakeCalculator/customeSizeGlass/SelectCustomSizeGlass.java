
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.customeSizeGlass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectCustomSizeGlass {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("excercise_time")
    @Expose
    private String excerciseTime;
    @SerializedName("glass_size_in_ml")
    @Expose
    private String glassSizeInMl;
    @SerializedName("recommended_water_in_ml")
    @Expose
    private Integer recommendedWaterInMl;
    @SerializedName("recommended_glass_of_water")
    @Expose
    private Integer recommendedGlassOfWater;
    @SerializedName("taken_glass_of_water")
    @Expose
    private Integer takenGlassOfWater;
    @SerializedName("recommended_duration_in_mins")
    @Expose
    private String recommendedDurationInMins;
    @SerializedName("taken_water_in_ml")
    @Expose
    private Double takenWaterInMl;
    @SerializedName("remaining_water_in_ml")
    @Expose
    private Double remainingWaterInMl;
    @SerializedName("duration_for_taken_glass_of_water")
    @Expose
    private String durationForTakenGlassOfWater;
    @SerializedName("remaining_time_for_glass_of_water")
    @Expose
    private String remainingTimeForGlassOfWater;
    @SerializedName("wake_up_time")
    @Expose
    private String wakeUpTime;
    @SerializedName("bed_time")
    @Expose
    private String bedTime;
    @SerializedName("added_glass_time")
    @Expose
    private String addedGlassTime;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getExcerciseTime() {
        return excerciseTime;
    }

    public void setExcerciseTime(String excerciseTime) {
        this.excerciseTime = excerciseTime;
    }

    public String getGlassSizeInMl() {
        return glassSizeInMl;
    }

    public void setGlassSizeInMl(String glassSizeInMl) {
        this.glassSizeInMl = glassSizeInMl;
    }

    public Integer getRecommendedWaterInMl() {
        return recommendedWaterInMl;
    }

    public void setRecommendedWaterInMl(Integer recommendedWaterInMl) {
        this.recommendedWaterInMl = recommendedWaterInMl;
    }

    public Integer getRecommendedGlassOfWater() {
        return recommendedGlassOfWater;
    }

    public void setRecommendedGlassOfWater(Integer recommendedGlassOfWater) {
        this.recommendedGlassOfWater = recommendedGlassOfWater;
    }

    public Integer getTakenGlassOfWater() {
        return takenGlassOfWater;
    }

    public void setTakenGlassOfWater(Integer takenGlassOfWater) {
        this.takenGlassOfWater = takenGlassOfWater;
    }

    public String getRecommendedDurationInMins() {
        return recommendedDurationInMins;
    }

    public void setRecommendedDurationInMins(String recommendedDurationInMins) {
        this.recommendedDurationInMins = recommendedDurationInMins;
    }

    public Double getTakenWaterInMl() {
        return takenWaterInMl;
    }

    public void setTakenWaterInMl(Double takenWaterInMl) {
        this.takenWaterInMl = takenWaterInMl;
    }

    public Double getRemainingWaterInMl() {
        return remainingWaterInMl;
    }

    public void setRemainingWaterInMl(Double remainingWaterInMl) {
        this.remainingWaterInMl = remainingWaterInMl;
    }

    public String getDurationForTakenGlassOfWater() {
        return durationForTakenGlassOfWater;
    }

    public void setDurationForTakenGlassOfWater(String durationForTakenGlassOfWater) {
        this.durationForTakenGlassOfWater = durationForTakenGlassOfWater;
    }

    public String getRemainingTimeForGlassOfWater() {
        return remainingTimeForGlassOfWater;
    }

    public void setRemainingTimeForGlassOfWater(String remainingTimeForGlassOfWater) {
        this.remainingTimeForGlassOfWater = remainingTimeForGlassOfWater;
    }

    public String getWakeUpTime() {
        return wakeUpTime;
    }

    public void setWakeUpTime(String wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    public String getBedTime() {
        return bedTime;
    }

    public void setBedTime(String bedTime) {
        this.bedTime = bedTime;
    }

    public String getAddedGlassTime() {
        return addedGlassTime;
    }

    public void setAddedGlassTime(String addedGlassTime) {
        this.addedGlassTime = addedGlassTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
