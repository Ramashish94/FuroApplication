
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestoredPlan {

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
    private Integer remainingWaterInMl;
    @SerializedName("taken_glass_of_water")
    @Expose
    private Integer takenGlassOfWater;
    @SerializedName("remaining_glass_of_water")
    @Expose
    private Integer remainingGlassOfWater;
    @SerializedName("duration_for_taken_glass_of_water")
    @Expose
    private String durationForTakenGlassOfWater;
    @SerializedName("remaining_time_for_glass_of_water")
    @Expose
    private String remainingTimeForGlassOfWater;
    @SerializedName("recommended_glass_of_water")
    @Expose
    private Integer recommendedGlassOfWater;
    @SerializedName("reminder_duration_in_mins")
    @Expose
    private String reminderDurationInMins;
    @SerializedName("recommended_duration_in_mins")
    @Expose
    private String recommendedDurationInMins;
    @SerializedName("is_selected")
    @Expose
    private Integer isSelected;
    @SerializedName("is_recomended")
    @Expose
    private Integer isRecomended;
    @SerializedName("added_glass_time")
    @Expose
    private String addedGlassTime;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

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

    public Integer getRemainingWaterInMl() {
        return remainingWaterInMl;
    }

    public void setRemainingWaterInMl(Integer remainingWaterInMl) {
        this.remainingWaterInMl = remainingWaterInMl;
    }

    public Integer getTakenGlassOfWater() {
        return takenGlassOfWater;
    }

    public void setTakenGlassOfWater(Integer takenGlassOfWater) {
        this.takenGlassOfWater = takenGlassOfWater;
    }

    public Integer getRemainingGlassOfWater() {
        return remainingGlassOfWater;
    }

    public void setRemainingGlassOfWater(Integer remainingGlassOfWater) {
        this.remainingGlassOfWater = remainingGlassOfWater;
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

    public Integer getRecommendedGlassOfWater() {
        return recommendedGlassOfWater;
    }

    public void setRecommendedGlassOfWater(Integer recommendedGlassOfWater) {
        this.recommendedGlassOfWater = recommendedGlassOfWater;
    }

    public String getReminderDurationInMins() {
        return reminderDurationInMins;
    }

    public void setReminderDurationInMins(String reminderDurationInMins) {
        this.reminderDurationInMins = reminderDurationInMins;
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

    public Integer getIsRecomended() {
        return isRecomended;
    }

    public void setIsRecomended(Integer isRecomended) {
        this.isRecomended = isRecomended;
    }

    public String getAddedGlassTime() {
        return addedGlassTime;
    }

    public void setAddedGlassTime(String addedGlassTime) {
        this.addedGlassTime = addedGlassTime;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

}
