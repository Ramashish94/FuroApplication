package com.app.furoapp.model.existsContact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExistContact {

    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("fqfrnd")
    @Expose
    private String fqfrnd;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("fqfrnd")


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFqfrnd() {
        return fqfrnd;
    }

    public void setFqfrnd(String fqfrnd) {
        this.fqfrnd = fqfrnd;
    }
}
