
package com.app.furoapp.model.draft;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DraftChallenge {

    @SerializedName("challenge_id")
    @Expose
    private String challengeId;
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
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
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

}
