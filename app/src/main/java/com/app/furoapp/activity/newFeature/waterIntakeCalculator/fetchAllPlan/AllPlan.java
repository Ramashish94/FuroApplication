
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllPlan {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("water_take_in_ml")
    @Expose
    private String waterTakeInMl;
    @SerializedName("recommended_duration_in_mins")
    @Expose
    private String recommendedDurationInMins;
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

    public String getWaterTakeInMl() {
        return waterTakeInMl;
    }

    public void setWaterTakeInMl(String waterTakeInMl) {
        this.waterTakeInMl = waterTakeInMl;
    }

    public String getRecommendedDurationInMins() {
        return recommendedDurationInMins;
    }

    public void setRecommendedDurationInMins(String recommendedDurationInMins) {
        this.recommendedDurationInMins = recommendedDurationInMins;
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
