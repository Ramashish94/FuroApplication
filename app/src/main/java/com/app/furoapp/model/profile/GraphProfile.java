package com.app.furoapp.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GraphProfile {

    @SerializedName("g_date")
    @Expose
    private String g_date;
    @SerializedName("count")
    @Expose
    private String count;

    public String getG_date() {
        return g_date;
    }

    public void setG_date(String g_date) {
        this.g_date = g_date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}