package com.example.bloodmatch.model;

import com.google.firebase.firestore.DocumentReference;

public class RequestModel {
    private String benefit, location;
    private Blood blood;
    private Motivation motivation;
    private DocumentReference inNeed;

    public RequestModel(){}

    public Blood getBlood() {
        return blood;
    }

    public void setBlood(Blood blood) {
        this.blood = blood;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Motivation getMotivation() {
        return motivation;
    }

    public void setMotivation(Motivation motivation) {
        this.motivation = motivation;
    }

    public DocumentReference getInNeed() {
        return inNeed;
    }

    public void setInNeed(DocumentReference inNeed) {
        this.inNeed = inNeed;
    }
}
