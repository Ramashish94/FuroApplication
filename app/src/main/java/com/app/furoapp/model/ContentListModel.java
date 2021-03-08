package com.app.furoapp.model;

public class ContentListModel {
    private int contentImage;
    private String feedType;
    private String feedTypeCategoryName;
    private int feedTypeCategoryImage;
    private String feedTitle;
    private Boolean isVideo;


    public int getContentImage() {
        return contentImage;
    }

    public void setContentImage(int contentImage) {
        this.contentImage = contentImage;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public String getFeedTypeCategoryName() {
        return feedTypeCategoryName;
    }

    public void setFeedTypeCategoryName(String feedTypeCategoryName) {
        this.feedTypeCategoryName = feedTypeCategoryName;
    }

    public int getFeedTypeCategoryImage() {
        return feedTypeCategoryImage;
    }

    public void setFeedTypeCategoryImage(int feedTypeCategoryImage) {
        this.feedTypeCategoryImage = feedTypeCategoryImage;
    }

    public String getFeedTitle() {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public Boolean getVideo() {
        return isVideo;
    }

    public void setVideo(Boolean video) {
        isVideo = video;
    }

}
