package com.app.furoapp.model.Settings;

public class UpdateUserModel {

    private String user_id;
    private String name;
    private String email;
    private String gender;

    private String mobile;

    public UpdateUserModel(String user_id, String name, String email, String gender,String mobile) {

        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.mobile = mobile;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String userId) {
        this.user_id = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
