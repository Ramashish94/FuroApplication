
package com.app.furoapp.model.challengeforyouRecieve;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallenegeForYouRecieve {

    @SerializedName("ReceiveChallenge")
    @Expose
    private List<ReceiveChallenge> receiveChallenge = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<ReceiveChallenge> getReceiveChallenge() {
        return receiveChallenge;
    }

    public void setReceiveChallenge(List<ReceiveChallenge> receiveChallenge) {
        this.receiveChallenge = receiveChallenge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
