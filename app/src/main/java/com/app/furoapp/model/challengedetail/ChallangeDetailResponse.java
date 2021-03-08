
package com.app.furoapp.model.challengedetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallangeDetailResponse {

    @SerializedName("challenge_details")
    @Expose
    private ChallengeDetails challengeDetails;
    @SerializedName("status")
    @Expose
    private String status;

    public ChallengeDetails getChallengeDetails() {
        return challengeDetails;
    }

    public void setChallengeDetails(ChallengeDetails challengeDetails) {
        this.challengeDetails = challengeDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
