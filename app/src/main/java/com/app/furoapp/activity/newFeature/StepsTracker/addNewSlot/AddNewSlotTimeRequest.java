package com.app.furoapp.activity.newFeature.StepsTracker.addNewSlot;

import com.google.gson.annotations.SerializedName;

public class AddNewSlotTimeRequest {

    @SerializedName("timeslot")
    private String timeslot;

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }


}