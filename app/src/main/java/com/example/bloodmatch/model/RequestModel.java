package com.example.bloodmatch.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.MotionEvent;

import com.google.firebase.firestore.DocumentReference;
import com.example.bloodmatch.data.DonorCollection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RequestModel extends Model{
    private String benefit, location;
    private Blood blood;
    private Motivation motivation;
    private Date createdAt;
    private DocumentReference inNeed;
    private List<DocumentReference> donors;

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

    public List<DocumentReference> getDonors() { return donors; }

    public void setDonors(List<DocumentReference> donors) { this.donors = donors; }
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
