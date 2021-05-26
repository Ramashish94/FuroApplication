
package com.app.furoapp.activity.newFeature.likeAndSaved.SavedList.saveOnPost;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavedListResponse {

    @SerializedName("savedOnPost")
    @Expose
    private List<SavedOnPost> savedOnPost = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<SavedOnPost> getSavedOnPost() {
        return savedOnPost;
    }

    public void setSavedOnPost(List<SavedOnPost> savedOnPost) {
        this.savedOnPost = savedOnPost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
