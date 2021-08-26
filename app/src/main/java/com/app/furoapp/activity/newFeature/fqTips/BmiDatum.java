
package com.app.furoapp.activity.newFeature.fqTips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BmiDatum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("paragraph")
    @Expose
    private String paragraph;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
