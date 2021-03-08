
package com.app.furoapp.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClubsMonthly {

    @SerializedName("finished")
    @Expose
    private String finished;
    @SerializedName("image")
    @Expose
    private String image;

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
