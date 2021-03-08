
package com.app.furoapp.model.chooseChallange;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subcategory {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("challenge_id")
    @Expose
    private String challengeId;
    @SerializedName("subcategory")
    @Expose
    private String subcategory;
    @SerializedName("black_icon")
    @Expose
    private String blackIcon;
    @SerializedName("blue_icon")
    @Expose
    private String blueIcon;
    @SerializedName("white_icon")
    @Expose
    private String whiteIcon;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("percentile")
    @Expose
    private String percentile;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getBlackIcon() {
        return blackIcon;
    }

    public void setBlackIcon(String blackIcon) {
        this.blackIcon = blackIcon;
    }

    public String getBlueIcon() {
        return blueIcon;
    }

    public void setBlueIcon(String blueIcon) {
        this.blueIcon = blueIcon;
    }

    public String getWhiteIcon() {
        return whiteIcon;
    }

    public void setWhiteIcon(String whiteIcon) {
        this.whiteIcon = whiteIcon;
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

    public String getPercentile() {
        return percentile;
    }

    public void setPercentile(String percentile) {
        this.percentile = percentile;
    }
}
