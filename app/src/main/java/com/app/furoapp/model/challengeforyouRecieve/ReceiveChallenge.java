
package com.app.furoapp.model.challengeforyouRecieve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReceiveChallenge {

    @SerializedName("challenge_id")
    @Expose
    private String challengeId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("count_challengers")
    @Expose
    private Integer countChallengers;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("challenge_name")
    @Expose
    private Object challengeName;
    @SerializedName("challenge_video")
    @Expose
    private String challengeVideo;
    @SerializedName("challenge_activity")
    @Expose
    private String challengeActivity;
    @SerializedName("challenge_type")
    @Expose
    private String challengeType;
    @SerializedName("challenged_by")
    @Expose
    private String challengedBy;

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCountChallengers() {
        return countChallengers;
    }

    public void setCountChallengers(Integer countChallengers) {
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

    public Object getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(Object challengeName) {
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

    public String getChallengedBy() {
        return challengedBy;
    }

    public void setChallengedBy(String challengedBy) {
        this.challengedBy = challengedBy;
    }

}
