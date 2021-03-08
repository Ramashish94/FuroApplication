
package com.app.furoapp.model.createVideoChallenge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateVideoChallangeResponse {

    @SerializedName("newchallenge")
    @Expose
    private Newchallenge newchallenge;
    @SerializedName("status")
    @Expose
    private String status;

    public Newchallenge getNewchallenge() {
        return newchallenge;
    }

    public void setNewchallenge(Newchallenge newchallenge) {
        this.newchallenge = newchallenge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
