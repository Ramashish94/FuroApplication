package com.app.furoapp.activity.newFeature.ContentEngagementModel.userView;

import com.google.gson.annotations.SerializedName;

public class ViewsRequest {
    @SerializedName("post_id")
    private int postId;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }


}