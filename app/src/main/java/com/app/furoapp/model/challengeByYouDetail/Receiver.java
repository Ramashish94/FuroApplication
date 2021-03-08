
package com.app.furoapp.model.challengeByYouDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Receiver {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("receiver_challenge")
    @Expose
    private ReceiverChallenge receiverChallenge;
    @SerializedName("status")
    @Expose
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ReceiverChallenge getReceiverChallenge() {
        return receiverChallenge;
    }

    public void setReceiverChallenge(ReceiverChallenge receiverChallenge) {
        this.receiverChallenge = receiverChallenge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
