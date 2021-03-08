package com.app.furoapp.model.FriendModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FriendListModel {
    @SerializedName("friend_list")
    @Expose
    private List<PendingFriendList> friendList = null;

    public List<PendingFriendList> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<PendingFriendList> friendList) {
        this.friendList = friendList;
    }

}
