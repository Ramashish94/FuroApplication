
package com.app.furoapp.model.challengeByUser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendChallenge {

    @SerializedName("challenge_id")
    @Expose
    private String challengeId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("count_challengers")
    @Expose
    private String countChallengers;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("challenge_name")
    @Expose
    private String challengeName;
    @SerializedName("challenge_video")
    @Expose
    private String challengeVideo;
    @SerializedName("challenge_activity")
    @Expose
    private String challengeActivity;
    @SerializedName("challenge_type")
    @Expose
    private String challengeType;

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCountChallengers() {
        return countChallengers;
    }

    public void setCountChallengers(String countChallengers) {
        this.countChallengers = countChallengers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getChallengeVideo() {
        return challengeVideo;
    }

    public void setChallengeVideo(String challengeVideo) {
        this.challengeVideo = challengeVideo;
    }

    public String getChallengeActivity() {
        return challengeActivity;
    }

    public void setChallengeActivity(String challengeActivity) {
        this.challengeActivity = challengeActivity;
    }

    public String getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(String challengeType) {
        this.challengeType = challengeType;
    }

}
