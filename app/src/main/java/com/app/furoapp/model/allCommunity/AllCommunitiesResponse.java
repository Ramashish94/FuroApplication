
package com.app.furoapp.model.allCommunity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllCommunitiesResponse {

    @SerializedName("communities")
    @Expose
    private List<Community> communities = null;

    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }

}
