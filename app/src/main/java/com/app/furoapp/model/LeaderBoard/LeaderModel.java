
package com.app.furoapp.model.LeaderBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderModel {

    @SerializedName("leadership")
    @Expose
    private Leadership leadership;

    public Leadership getLeadership() {
        return leadership;
    }

    public void setLeadership(Leadership leadership) {
        this.leadership = leadership;
    }

}
