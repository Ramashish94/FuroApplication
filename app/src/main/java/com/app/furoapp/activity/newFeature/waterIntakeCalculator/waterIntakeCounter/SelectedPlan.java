
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectedPlan {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("water_take_in_ml")
    @Expose
    private Integer waterTakeInMl;
    @SerializedName("taken_water_in_ml")
    @Expose
    private Integer takenWaterInMl;
    @SerializedName("remaining_water_in_ml")
    @Expose
    private Object remainingWaterInMl;
    @SerializedName("taken_glass_of_water")
    @Expose
    private Integer takenGlassOfWater;
    @SerializedName("remaining_glass_of_water")
    @Expose
    private Object remainingGlassOfWater;
    @SerializedName("duration_for_taken_glass_of_water")
    @Expose
    private Object durationForTakenGlassOfWater;
    @SerializedName("remaining_time_for_glass_of_water")
    @Expose
    private Object remainingTimeForGlassOfWater;
    @SerializedName("recommended_glass_of_water")
    @Expose
    private Object recommendedGlassOfWater;
    @SerializedName("recommended_duration_in_mins")
    @Expose
    private String recommendedDurationInMins;
    @SerializedName("is_selected")
    @Expose
    private Integer isSelected;
    @SerializedName("added_glass_time")
    @Expose
    private Object addedGlassTime;
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

    public Integer getWaterTakeInMl() {
        return waterTakeInMl;
    }

    public void setWaterTakeInMl(Integer waterTakeInMl) {
        this.waterTakeInMl = waterTakeInMl;
    }

    public Integer getTakenWaterInMl() {
        return takenWaterInMl;
    }

    public void setTakenWaterInMl(Integer takenWaterInMl) {
        this.takenWaterInMl = takenWaterInMl;
    }

    public Object getRemainingWaterInMl() {
        return remainingWaterInMl;
    }

    public void setRemainingWaterInMl(Object remainingWaterInMl) {
        this.remainingWaterInMl = remainingWaterInMl;
    }

    public Integer getTakenGlassOfWater() {
        return takenGlassOfWater;
    }

    public void setTakenGlassOfWater(Integer takenGlassOfWater) {
        this.takenGlassOfWater = takenGlassOfWater;
    }

    public Object getRemainingGlassOfWater() {
        return remainingGlassOfWater;
    }

    public void setRemainingGlassOfWater(Object remainingGlassOfWater) {
        this.remainingGlassOfWater = remainingGlassOfWater;
    }

    public Object getDurationForTakenGlassOfWater() {
        return durationForTakenGlassOfWater;
    }

    public void setDurationForTakenGlassOfWater(Object durationForTakenGlassOfWater) {
        this.durationForTakenGlassOfWater = durationForTakenGlassOfWater;
    }

    public Object getRemainingTimeForGlassOfWater() {
        return remainingTimeForGlassOfWater;
    }

    public void setRemainingTimeForGlassOfWater(Object remainingTimeForGlassOfWater) {
        this.remainingTimeForGlassOfWater = remainingTimeForGlassOfWater;
    }

    public Object getRecommendedGlassOfWater() {
        return recommendedGlassOfWater;
    }

    public void setRecommendedGlassOfWater(Object recommendedGlassOfWater) {
        this.recommendedGlassOfWater = recommendedGlassOfWater;
    }

    public String getRecommendedDurationInMins() {
        return recommendedDurationInMins;
    }

    public void setRecommendedDurationInMins(String recommendedDurationInMins) {
        this.recommendedDurationInMins = recommendedDurationInMins;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }

    public Object getAddedGlassTime() {
        return addedGlassTime;
    }

    public void setAddedGlassTime(Object addedGlassTime) {
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
