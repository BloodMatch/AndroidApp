package com.example.bloodmatch.models;

public class Request {
    private String title;
    private String description;
    private String media;
    private String state;
    private String bloodGroup;

    public Request() {
    }

    public Request(String title, String desc, String media, String state, String bloodGroup){
        this.bloodGroup = bloodGroup;
        this.description = desc;
        this.media = media;
        this.state = state;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
