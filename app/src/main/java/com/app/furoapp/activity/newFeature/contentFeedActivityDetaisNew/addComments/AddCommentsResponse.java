
package com.app.furoapp.activity.newFeature.contentFeedActivityDetaisNew.addComments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCommentsResponse {

    @SerializedName("com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.Comment")
    @Expose
    private Comment comment;
    @SerializedName("status")
    @Expose
    private String status;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
