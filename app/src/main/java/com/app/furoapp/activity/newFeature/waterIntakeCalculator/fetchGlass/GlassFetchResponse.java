
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchGlass;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlassFetchResponse {

    @SerializedName("userGlassSizes")
    @Expose
    private List<UserGlassSize> userGlassSizes = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<UserGlassSize> getUserGlassSizes() {
        return userGlassSizes;
    }

    public void setUserGlassSizes(List<UserGlassSize> userGlassSizes) {
        this.userGlassSizes = userGlassSizes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
