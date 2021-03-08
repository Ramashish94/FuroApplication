
package com.app.furoapp.model.winnerApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataLoser {

    @SerializedName("loser")
    @Expose
    private Loser loser;

    public Loser getLoser() {
        return loser;
    }

    public void setLoser(Loser loser) {
        this.loser = loser;
    }

}
