
package com.app.furoapp.model.communitydetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Leadership {

    @SerializedName("target")
    @Expose
    private String target;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
