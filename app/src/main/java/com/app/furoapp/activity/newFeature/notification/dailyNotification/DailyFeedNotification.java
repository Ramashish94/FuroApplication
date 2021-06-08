
package com.app.furoapp.activity.newFeature.notification.dailyNotification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyFeedNotification {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("notification_type")
    @Expose
    private String notificationType;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("msg_type")
    @Expose
    private String msgType;
    @SerializedName("who_gets_notified")
    @Expose
    private String whoGetsNotified;
    @SerializedName("how_notified")
    @Expose
    private String howNotified;
    @SerializedName("content_type")
    @Expose
    private Object contentType;
    @SerializedName("content_repeat_auto")
    @Expose
    private Object contentRepeatAuto;
    @SerializedName("content_title")
    @Expose
    private Object contentTitle;
    @SerializedName("content_description")
    @Expose
    private Object contentDescription;
    @SerializedName("blog_id")
    @Expose
    private Object blogId;
    @SerializedName("deep_link")
    @Expose
    private Object deepLink;
    @SerializedName("content_datetime")
    @Expose
    private String contentDatetime;
    @SerializedName("content_username")
    @Expose
    private Object contentUsername;
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

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getWhoGetsNotified() {
        return whoGetsNotified;
    }

    public void setWhoGetsNotified(String whoGetsNotified) {
        this.whoGetsNotified = whoGetsNotified;
    }

    public String getHowNotified() {
        return howNotified;
    }

    public void setHowNotified(String howNotified) {
        this.howNotified = howNotified;
    }

    public Object getContentType() {
        return contentType;
    }

    public void setContentType(Object contentType) {
        this.contentType = contentType;
    }

    public Object getContentRepeatAuto() {
        return contentRepeatAuto;
    }

    public void setContentRepeatAuto(Object contentRepeatAuto) {
        this.contentRepeatAuto = contentRepeatAuto;
    }

    public Object getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(Object contentTitle) {
        this.contentTitle = contentTitle;
    }

    public Object getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(Object contentDescription) {
        this.contentDescription = contentDescription;
    }

    public Object getBlogId() {
        return blogId;
    }

    public void setBlogId(Object blogId) {
        this.blogId = blogId;
    }

    public Object getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(Object deepLink) {
        this.deepLink = deepLink;
    }

    public String getContentDatetime() {
        return contentDatetime;
    }

    public void setContentDatetime(String contentDatetime) {
        this.contentDatetime = contentDatetime;
    }

    public Object getContentUsername() {
        return contentUsername;
    }

    public void setContentUsername(Object contentUsername) {
        this.contentUsername = contentUsername;
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
