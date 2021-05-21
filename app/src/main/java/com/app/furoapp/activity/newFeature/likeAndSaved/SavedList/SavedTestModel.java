package com.app.furoapp.activity.newFeature.likeAndSaved.SavedList;

import android.widget.ImageView;

public class SavedTestModel {

    String tittle;
    String defineText;
    ImageView img;

    @Override
    public String toString() {
        return "SavedBookMarkedTestModel{" +
                "tittle='" + tittle + '\'' +
                ", defineText='" + defineText + '\'' +
                ", img=" + img +
                '}';
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDefineText() {
        return defineText;
    }

    public void setDefineText(String defineText) {
        this.defineText = defineText;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

}
