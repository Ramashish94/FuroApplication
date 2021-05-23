
package com.app.furoapp.activity.newFeature.contentFeedActivityDetaisNew.totComments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("post_id")
    @Expose
    private Integer postId;
    @SerializedName("total_comments")
    @Expose
    private Integer totalComments;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
    }

}
