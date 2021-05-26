
package com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User__1 {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("image")
    @Expose
    private Object image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

}
