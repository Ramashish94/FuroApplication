
package com.app.furoapp.model.mapchallenge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallengeDetails {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("acitivity_duration")
    @Expose
    private String acitivityDuration;
    @SerializedName("activity_count")
    @Expose
    private Object activityCount;
    @SerializedName("challenge_name")
    @Expose
    private Object challengeName;
    @SerializedName("challenge_activity")
    @Expose
    private String challengeActivity;
    @SerializedName("accept_challenge_id")
    @Expose
    private Object acceptChallengeId;
    @SerializedName("file")
    @Expose
    private Object file;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("black_icon")
    @Expose
    private String blackIcon;
    @SerializedName("white_icon")
    @Expose
    private String whiteIcon;
    @SerializedName("blue_icon")
    @Expose
    private String blueIcon;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("userimage")
    @Expose
    private String userimage;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("expired")
    @Expose
    private String expired;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;

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

    public String getAcitivityDuration() {
        return acitivityDuration;
    }

    public void setAcitivityDuration(String acitivityDuration) {
        this.acitivityDuration = acitivityDuration;
    }

    public Object getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(Object activityCount) {
        this.activityCount = activityCount;
    }

    public Object getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(Object challengeName) {
        this.challengeName = challengeName;
    }

    public String getChallengeActivity() {
        return challengeActivity;
    }

    public void setChallengeActivity(String challengeActivity) {
        this.challengeActivity = challengeActivity;
    }

    public Object getAcceptChallengeId() {
        return acceptChallengeId;
    }

    public void setAcceptChallengeId(Object acceptChallengeId) {
        this.acceptChallengeId = acceptChallengeId;
    }

    public Object getFile() {
        return file;
    }

    public void setFile(Object file) {
        this.file = file;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
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
