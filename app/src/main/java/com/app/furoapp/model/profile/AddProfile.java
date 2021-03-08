package com.app.furoapp.model.profile;

public class AddProfile {

    private String user_id;
    private String activity;

    public AddProfile(String user_id, String activity) {
        this.user_id = user_id;
        this.activity = activity;
    }

    public AddProfile(String user_id)
    {
        this.user_id = user_id;
    }


}
