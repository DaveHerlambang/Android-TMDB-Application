package com.example.androidmobile;

public class Movies {
    String id;
    String name;
    String img;
    String date;
    String overview;
    String rating;

    public Movies(String id, String name, String img, String date, String overview, String rating) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.date = date;
        this.overview = overview;
        this.rating = rating;
    }

    public Movies() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
