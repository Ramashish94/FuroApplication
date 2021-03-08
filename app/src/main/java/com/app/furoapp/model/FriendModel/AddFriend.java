package com.app.furoapp.model.FriendModel;

public class AddFriend {

    private String user_id;
    private String friend_id;

    public AddFriend(String user_id, String friend_id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
    }

    public AddFriend(String user_id) {
        this.user_id = user_id;
    }


}
