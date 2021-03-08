
package com.app.furoapp.model.content_feed;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("activities")
    @Expose
    private List<Activity> activities = null;
    @SerializedName("food")
    @Expose
    private List<Food> food = null;
    @SerializedName("motivation")
    @Expose
    private List<Motivation> motivation = null;
    @SerializedName("health")
    @Expose
    private List<Health> health = null;

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<Food> getFood() {
        return food;
    }

    public void setFood(List<Food> food) {
        this.food = food;
    }

    public List<Motivation> getMotivation() {
        return motivation;
    }

    public void setMotivation(List<Motivation> motivation) {
        this.motivation = motivation;
    }

    public List<Health> getHealth() {
        return health;
    }

    public void setHealth(List<Health> health) {
        this.health = health;
    }

}
