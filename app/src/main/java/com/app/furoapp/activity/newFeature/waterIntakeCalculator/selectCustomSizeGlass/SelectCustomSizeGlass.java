
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.selectCustomSizeGlass;

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
    private Double recommendedGlassOfWater;
    @SerializedName("selected_water_in_ml")
    @Expose
    private Integer selectedWaterInMl;
    @SerializedName("taken_glass_of_water")
    @Expose
    private Integer takenGlassOfWater;
    @SerializedName("recommended_duration_in_mins")
    @Expose
    private Double recommendedDurationInMins;
    @SerializedName("taken_water_in_ml")
    @Expose
    private Integer takenWaterInMl;
    @SerializedName("remaining_water_in_ml")
    @Expose
    private Integer remainingWaterInMl;
    @SerializedName("duration_for_taken_glass_of_water")
    @Expose
    private Double durationForTakenGlassOfWater;
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

    public Double getRecommendedGlassOfWater() {
        return recommendedGlassOfWater;
    }

    public void setRecommendedGlassOfWater(Double recommendedGlassOfWater) {
        this.recommendedGlassOfWater = recommendedGlassOfWater;
    }

    public Integer getSelectedWaterInMl() {
        return selectedWaterInMl;
    }

    public void setSelectedWaterInMl(Integer selectedWaterInMl) {
        this.selectedWaterInMl = selectedWaterInMl;
    }

    public Integer getTakenGlassOfWater() {
        return takenGlassOfWater;
    }

    public void setTakenGlassOfWater(Integer takenGlassOfWater) {
        this.takenGlassOfWater = takenGlassOfWater;
    }

    public Double getRecommendedDurationInMins() {
        return recommendedDurationInMins;
    }

    public void setRecommendedDurationInMins(Double recommendedDurationInMins) {
        this.recommendedDurationInMins = recommendedDurationInMins;
    }

    public Integer getTakenWaterInMl() {
        return takenWaterInMl;
    }

    public void setTakenWaterInMl(Integer takenWaterInMl) {
        this.takenWaterInMl = takenWaterInMl;
    }

    public Integer getRemainingWaterInMl() {
        return remainingWaterInMl;
    }

    public void setRemainingWaterInMl(Integer remainingWaterInMl) {
        this.remainingWaterInMl = remainingWaterInMl;
    }

    public Double getDurationForTakenGlassOfWater() {
        return durationForTakenGlassOfWater;
    }

    public void setDurationForTakenGlassOfWater(Double durationForTakenGlassOfWater) {
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
