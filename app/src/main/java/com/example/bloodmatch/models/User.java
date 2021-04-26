package com.example.bloodmatch.models;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private String cin;
    private Date birthday;
    private String gender;
    private String bloodGroup;
    private String city;
    private String zipCode;
    private Boolean isDonner;

    /*
    * Constructor
    * */
    public User(){ }

    public User(String cin, Date birthday, String gender, String bloodGroup, String city, String zipCode, Boolean isDonner){
        this.cin = cin;
        this.birthday = birthday;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.zipCode = zipCode;
        this.isDonner = isDonner;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Boolean getDonner() {
        return isDonner;
    }

    public void setDonner(Boolean donner) {
        isDonner = donner;
    }
}
