
package com.app.furoapp.activity.newFeature.contentFeedActivityDetaisNew.totComments;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotelCommentsResponse {

    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
