
package com.app.furoapp.model.existsContact;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExistsContactResponse {

    @SerializedName("existContacts")
    @Expose
    private List<ExistContact> existContacts = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<ExistContact> getExistContacts() {
        return existContacts;
    }

    public void setExistContacts(List<ExistContact> existContacts) {
        this.existContacts = existContacts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
