package com.app.furoapp.model.profile;

public class ItemProfile {
    public String categoryName;
    public String name;
    public String imageName;
    private boolean isSelected;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}