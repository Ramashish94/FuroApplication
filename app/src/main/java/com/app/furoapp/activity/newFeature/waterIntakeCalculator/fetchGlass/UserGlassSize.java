
package com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchGlass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserGlassSize {

    @SerializedName("glass_size_in_ml")
    @Expose
    private String glassSizeInMl;
    /*added two keys glass size nd check sign */
    @SerializedName("glass_size")
    @Expose
    private String glassSize;
    @SerializedName("check_sign")
    @Expose
    private String checksign;

    public String getGlassSizeInMl() {
        return glassSizeInMl;
    }

    public void setGlassSizeInMl(String glassSizeInMl) {
        this.glassSizeInMl = glassSizeInMl;
    }


    public String getGlassSize() {
        return glassSize;
    }

    public void setGlassSize(String glassSize) {
        this.glassSize = glassSize;
    }

    public String getChecksign() {
        return checksign;
    }

    public void setChecksign(String checksign) {
        this.checksign = checksign;
    }


}
