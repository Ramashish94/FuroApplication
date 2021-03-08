
package com.app.furoapp.model.communityChallange;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Challenge {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("community_id")
    @Expose
    private String communityId;
    @SerializedName("challenge")
    @Expose
    private String challenge;
    @SerializedName("completion_rate")
    @Expose
    private String completionRate;
    @SerializedName("target")
    @Expose
    private String target;
    @SerializedName("timeline")
    @Expose
    private String timeline;
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

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(String completionRate) {
        this.completionRate = completionRate;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
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
