package com.app.furoapp.model.getImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserImageResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("image")
    @Expose
    private String image;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}