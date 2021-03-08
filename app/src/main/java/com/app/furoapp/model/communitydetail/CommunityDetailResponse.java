
package com.app.furoapp.model.communitydetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommunityDetailResponse {

    @SerializedName("communities")
    @Expose
    private Communities communities;

    public Communities getCommunities() {
        return communities;
    }

    public void setCommunities(Communities communities) {
        this.communities = communities;
    }

}
