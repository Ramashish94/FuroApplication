package com.app.furoapp.model.FriendModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Friends {

    @SerializedName("particpants")
    @Expose
    public List<Particpant> particpants = null;

    public List<Particpant> getParticpants() {
        return particpants;
    }

    public void setParticpants(List<Particpant> particpants) {
        this.particpants = particpants;
    }
}
