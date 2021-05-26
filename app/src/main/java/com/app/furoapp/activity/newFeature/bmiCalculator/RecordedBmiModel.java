package com.app.furoapp.activity.newFeature.bmiCalculator;

public class RecordedBmiModel {
    String bmiScore;
    String gender;
    String weight;
    String height;

    @Override
    public String toString() {
        return "RecordedBmiModel{" +
                "bmiScore='" + bmiScore + '\'' +
                ", gender='" + gender + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                '}';
    }


    public String getBmiScore() {
        return bmiScore;
    }

    public void setBmiScore(String bmiScore) {
        this.bmiScore = bmiScore;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }


}
