package com.example.bloodmatch.model;

import com.google.firebase.firestore.DocumentReference;
import com.example.bloodmatch.data.DonorCollection;

public class RequestModel {
    private String benefit, location;
    private Blood blood;
    private Motivation motivation;
    private DocumentReference inNeed;
    private DonorModel donor;

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

    public DonorModel getRecipient(){
        return donor;
    }

    public void setRecipient(DonorModel donor){
        this.donor = donor;
    }
}
