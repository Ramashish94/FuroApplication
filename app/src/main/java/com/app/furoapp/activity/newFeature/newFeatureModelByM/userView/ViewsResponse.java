
package com.app.furoapp.activity.newFeature.newFeatureModelByM.userView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewsResponse {

    @SerializedName("view")
    @Expose
    private View view;
    @SerializedName("status")
    @Expose
    private String status;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
