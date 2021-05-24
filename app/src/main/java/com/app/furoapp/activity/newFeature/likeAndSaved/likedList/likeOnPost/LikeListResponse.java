
package com.app.furoapp.activity.newFeature.likeAndSaved.likedList.likeOnPost;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeListResponse {

    @SerializedName("likeOnPost")
    @Expose
    private List<LikeOnPost> likeOnPost = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<LikeOnPost> getLikeOnPost() {
        return likeOnPost;
    }

    public void setLikeOnPost(List<LikeOnPost> likeOnPost) {
        this.likeOnPost = likeOnPost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
