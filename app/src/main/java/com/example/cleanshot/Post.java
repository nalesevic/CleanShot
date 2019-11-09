package com.example.cleanshot;

public class Post {
    private String title;
    private String location;
    private String description;

    public Post(String title, String location, String description) {
        this.title = title;
        this.location = location;
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
}
