
package com.app.furoapp.model.communitymembers;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommunityMembersResponse {

    @SerializedName("members")
    @Expose
    private List<Member> members = null;

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

}
