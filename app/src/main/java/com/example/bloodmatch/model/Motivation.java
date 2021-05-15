package com.example.bloodmatch.model;

import java.io.Serializable;

public class Motivation implements Serializable {
    private String text;
    private String media1Url, media2Url, media3Url, media4Url;

    public Motivation(){}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMedia1Url() {
        return media1Url;
    }

    public void setMedia1Url(String media1Url) {
        this.media1Url = media1Url;
    }

    public String getMedia2Url() {
        return media2Url;
    }

    public void setMedia2Url(String media2Url) {
        this.media2Url = media2Url;
    }

    public String getMedia3Url() {
        return media3Url;
    }

    public void setMedia3Url(String media3Url) {
        this.media3Url = media3Url;
    }

    public String getMedia4Url() {
        return media4Url;
    }

    public void setMedia4Url(String media4Url) {
        this.media4Url = media4Url;
    }
}
