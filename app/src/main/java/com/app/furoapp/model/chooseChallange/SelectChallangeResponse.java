
package com.app.furoapp.model.chooseChallange;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectChallangeResponse {

    @SerializedName("challenges")
    @Expose
    private List<Challenge> challenges = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
