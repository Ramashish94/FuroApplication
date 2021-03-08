
package com.app.furoapp.model.communitydetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Communities {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("community_id")
    @Expose
    private Integer communityId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("target")
    @Expose
    private String target;
    @SerializedName("members")
    @Expose
    private String members;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("measurement")
    @Expose
    private String measurement;
    @SerializedName("measurement_icon")
    @Expose
    private String measurementIcon;
    @SerializedName("black_icon")
    @Expose
    private String blackIcon;
    @SerializedName("white_icon")
    @Expose
    private String whiteIcon;
    @SerializedName("blue_icon")
    @Expose
    private String blueIcon;
    @SerializedName("is_joined")
    @Expose
    private String isJoined;
    @SerializedName("leadership")
    @Expose
    private List<Leadership> leadership = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
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

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getMeasurementIcon() {
        return measurementIcon;
    }

    public void setMeasurementIcon(String measurementIcon) {
        this.measurementIcon = measurementIcon;
    }

    public String getBlackIcon() {
        return blackIcon;
    }

    public void setBlackIcon(String blackIcon) {
        this.blackIcon = blackIcon;
    }

    public String getWhiteIcon() {
        return whiteIcon;
    }

    public void setWhiteIcon(String whiteIcon) {
        this.whiteIcon = whiteIcon;
    }

    public String getBlueIcon() {
        return blueIcon;
    }

    public void setBlueIcon(String blueIcon) {
        this.blueIcon = blueIcon;
    }

    public String getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(String isJoined) {
        this.isJoined = isJoined;
    }

    public List<Leadership> getLeadership() {
        return leadership;
    }

    public void setLeadership(List<Leadership> leadership) {
        this.leadership = leadership;
    }

}
