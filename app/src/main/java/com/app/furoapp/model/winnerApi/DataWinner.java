
package com.app.furoapp.model.winnerApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataWinner {

    @SerializedName("winner")
    @Expose
    private Winner winner;

    public Winner getWinner() {
        return winner;
    }

    public void setWinner(Winner winner) {
        this.winner = winner;
    }

}
