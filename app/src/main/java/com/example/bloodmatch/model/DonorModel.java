package com.example.bloodmatch.model;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DonorModel extends UserModel {
    //private String id;
    private String cin, gender, birthDate, city;
    private boolean available = true;
    private Donation donation;
    private Blood blood;
    private Integer zipCode;
    private List<DocumentReference> requests;

    public static class Donation{
        private String firstTime, lastTime;
        private Integer frequency, quantity;

        public Donation(){ }

        public String getFirstTime() { return firstTime; }

        public void setFirstTime(String firstTime) { this.firstTime = firstTime; }

        public Integer getQuantity() { return quantity; }

        public void setQuantity(Integer quantity) { this.quantity = quantity; }

        public Integer getFrequency() { return frequency; }

        public void setFrequency(Integer frequency) { this.frequency = frequency; }

        public String getLastTime() { return lastTime; }

        public void setLastTime(String lastTime) { this.lastTime = lastTime; }
    }

    public DonorModel(){
        super();
        this.donation = new Donation();
    }

    public boolean getAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }

    public String getCin() { return cin; }

    public void setCin(String cin) { this.cin = cin; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getBirthDate() { return birthDate; }

    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public Integer getZipCode() { return zipCode; }

    public void setZipCode(Integer zipCode) { this.zipCode = zipCode; }

    public Blood getBlood(){ return blood; }

    public void setBlood(Blood blood) { this.blood = blood; }

    public Donation getDonation() { return donation; }

    public void setDonation(Donation donation) { this.donation = donation; }

    public Integer getAge(){ return 0; }

    public String getAddress(){ return ((zipCode == null)? "": zipCode + " ,") + city; }

    public List<DocumentReference> getRequests() { return requests; }

    public void setRequests(List<DocumentReference> requests) { this.requests = requests; }
}
