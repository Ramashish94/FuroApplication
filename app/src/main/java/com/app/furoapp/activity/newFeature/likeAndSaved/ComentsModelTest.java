package com.app.furoapp.activity.newFeature.likeAndSaved;

public class ComentsModelTest {
    String name;
    String comments;


    @Override
    public String toString() {
        return "ComentsModelTest{" +
                "name='" + name + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
