
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.cupCreate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CupCreateResponse {

    @SerializedName("AddUserCup")
    @Expose
    private AddUserCup addUserCup;
    @SerializedName("status")
    @Expose
    private String status;

    public AddUserCup getAddUserCup() {
        return addUserCup;
    }

    public void setAddUserCup(AddUserCup addUserCup) {
        this.addUserCup = addUserCup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
