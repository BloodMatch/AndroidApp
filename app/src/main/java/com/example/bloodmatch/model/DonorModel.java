package com.example.bloodmatch.model;

import java.io.Serializable;

public class DonorModel implements Serializable {
    //private String id;
    private String cin, gender, birthDate, city,
    firstTime, lastTime;
    private Blood blood;
    private Integer zipCode, frequency, quantity;

    public DonorModel(){ super(); }

    //public String getId() { return id; }

    //public void setId(String id) { this.id = id; }

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

    public String getFirstTime() { return firstTime; }

    public void setFirstTime(String firstTime) { this.firstTime = firstTime; }

    public Integer getQuantity() { return quantity; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getFrequency() { return frequency; }

    public void setFrequency(Integer frequency) { this.frequency = frequency; }

    public String getLastTime() { return lastTime; }

    public void setLastTime(String lastTime) { this.lastTime = lastTime; }
}
