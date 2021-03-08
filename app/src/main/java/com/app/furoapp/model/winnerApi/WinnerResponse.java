
package com.app.furoapp.model.winnerApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WinnerResponse {

    @SerializedName("data_winner")
    @Expose
    private DataWinner dataWinner;
    @SerializedName("data_loser")
    @Expose
    private DataLoser dataLoser;

    public DataWinner getDataWinner() {
        return dataWinner;
    }

    public void setDataWinner(DataWinner dataWinner) {
        this.dataWinner = dataWinner;
    }

    public DataLoser getDataLoser() {
        return dataLoser;
    }

    public void setDataLoser(DataLoser dataLoser) {
        this.dataLoser = dataLoser;
    }

}
