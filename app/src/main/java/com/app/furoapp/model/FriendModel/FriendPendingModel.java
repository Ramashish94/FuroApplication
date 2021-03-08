package com.app.furoapp.model.FriendModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FriendPendingModel {
    @SerializedName("pending_friend_list")
    @Expose
    private List<PendingFriendList> pendingFriendList = null;

    public List<PendingFriendList> getPendingFriendList() {
        return pendingFriendList;
    }

    public void setPendingFriendList(List<PendingFriendList> pendingFriendList) {
        this.pendingFriendList = pendingFriendList;
    }
}
