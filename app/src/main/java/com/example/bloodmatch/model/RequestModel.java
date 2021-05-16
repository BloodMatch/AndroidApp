package com.example.bloodmatch.model;

import com.google.firebase.firestore.DocumentReference;
import com.example.bloodmatch.data.DonorCollection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestModel {
    private String benefit, location;
    private Blood blood;
    private Motivation motivation;
    private Date createdAt;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getFormatedTime(){
        // formatting timestamp
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        long milliseconds = createdAt.getTime();
        return df.format(new Date(milliseconds));
    }
}
