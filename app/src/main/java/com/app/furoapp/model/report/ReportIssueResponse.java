
package com.app.furoapp.model.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportIssueResponse {

    @SerializedName("report")
    @Expose
    private Report report;
    @SerializedName("status")
    @Expose
    private String status;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
