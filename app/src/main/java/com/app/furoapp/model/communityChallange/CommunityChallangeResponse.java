
package com.app.furoapp.model.communityChallange;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommunityChallangeResponse {

    @SerializedName("challenges")
    @Expose
    private List<Challenge> challenges = null;

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

}
