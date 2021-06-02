
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.customeSizeGlass;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomGlassSizeResponse {

    @SerializedName("selectCustomSizeGlass")
    @Expose
    private SelectCustomSizeGlass selectCustomSizeGlass;
    @SerializedName("status")
    @Expose
    private String status;

    public SelectCustomSizeGlass getSelectCustomSizeGlass() {
        return selectCustomSizeGlass;
    }

    public void setSelectCustomSizeGlass(SelectCustomSizeGlass selectCustomSizeGlass) {
        this.selectCustomSizeGlass = selectCustomSizeGlass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
