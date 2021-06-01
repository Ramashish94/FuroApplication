
package com.app.furoapp.activity.newFeature.notification.notificationModel;

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
    private Object category;
    @SerializedName("msg_type")
    @Expose
    private Object msgType;
    @SerializedName("who_gets_notified")
    @Expose
    private Object whoGetsNotified;
    @SerializedName("how_notified")
    @Expose
    private Object howNotified;
    @SerializedName("content_type")
    @Expose
    private String contentType;
    @SerializedName("content_repeat_auto")
    @Expose
    private Object contentRepeatAuto;
    @SerializedName("content_title")
    @Expose
    private String contentTitle;
    @SerializedName("content_description")
    @Expose
    private Object contentDescription;
    @SerializedName("blog_id")
    @Expose
    private Integer blogId;
    @SerializedName("deep_link")
    @Expose
    private String deepLink;
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

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public Object getMsgType() {
        return msgType;
    }

    public void setMsgType(Object msgType) {
        this.msgType = msgType;
    }

    public Object getWhoGetsNotified() {
        return whoGetsNotified;
    }

    public void setWhoGetsNotified(Object whoGetsNotified) {
        this.whoGetsNotified = whoGetsNotified;
    }

    public Object getHowNotified() {
        return howNotified;
    }

    public void setHowNotified(Object howNotified) {
        this.howNotified = howNotified;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Object getContentRepeatAuto() {
        return contentRepeatAuto;
    }

    public void setContentRepeatAuto(Object contentRepeatAuto) {
        this.contentRepeatAuto = contentRepeatAuto;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public Object getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(Object contentDescription) {
        this.contentDescription = contentDescription;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(String deepLink) {
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
