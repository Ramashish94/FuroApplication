
package com.app.furoapp.model.draft;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DraftResponse {

    @SerializedName("draft_challenges")
    @Expose
    private List<DraftChallenge> draftChallenges = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<DraftChallenge> getDraftChallenges() {
        return draftChallenges;
    }

    public void setDraftChallenges(List<DraftChallenge> draftChallenges) {
        this.draftChallenges = draftChallenges;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
