
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.changeGlassSize;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserChangeGlassSizeResponse {

    @SerializedName("userChangeGlassSize")
    @Expose
    private UserChangeGlassSize userChangeGlassSize;
    @SerializedName("status")
    @Expose
    private String status;

    public UserChangeGlassSize getUserChangeGlassSize() {
        return userChangeGlassSize;
    }

    public void setUserChangeGlassSize(UserChangeGlassSize userChangeGlassSize) {
        this.userChangeGlassSize = userChangeGlassSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
