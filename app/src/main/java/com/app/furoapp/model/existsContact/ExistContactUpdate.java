package com.app.furoapp.model.existsContact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExistContactUpdate {

    private String mobile;
    private Integer userId;
    private String name;
    private String username;
    private String image;
    private String fqfrnd;
    private boolean isChecked;
    private boolean isSend;
    private boolean isFrndReqsend;


    public ExistContactUpdate(String mobile, Integer userId, String name, String username, String image,String fqfrnd, boolean isChecked, boolean isSend,boolean isFrndReqsend) {
        this.mobile = mobile;
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.image = image;
        this.isChecked = isChecked;
        this.isSend = isSend;
        this.fqfrnd = fqfrnd;
        this.isFrndReqsend = isFrndReqsend;

    }



    public boolean getChecked() {
        return isChecked;
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }


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

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public boolean isFrndReqsend() {
        return isFrndReqsend;
    }

    public void setFrndReqsend(boolean frndReqsend) {
        isFrndReqsend = frndReqsend;
    }
}
