
package com.app.furoapp.model.communityJoin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommunitiesJoinResponse {

    @SerializedName("community")
    @Expose
    private Community community;

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

}
