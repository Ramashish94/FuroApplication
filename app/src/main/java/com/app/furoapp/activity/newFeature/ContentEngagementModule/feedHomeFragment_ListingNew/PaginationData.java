package com.app.furoapp.activity.newFeature.ContentEngagementModule.feedHomeFragment_ListingNew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaginationData {

    @SerializedName("data")
    @Expose
    private List<Datum> data= null;

    @SerializedName("current_page")
    @Expose
    private  int currentPage;

    @SerializedName("last_page")
    @Expose
    private int lastPage ;

    @SerializedName("total")
    @Expose
    private  int total;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
