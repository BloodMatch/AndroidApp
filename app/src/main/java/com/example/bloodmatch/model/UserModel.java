package com.example.bloodmatch.model;

public class UserModel {
    private String DisplayName, Email, IsEmailVerified,  PhoneNumber, PhotoUrl, Password;

    public  UserModel(){}

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPassword() {
        return Password;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setIsEmailVerified(String isEmailVerified) {
        IsEmailVerified = isEmailVerified;
    }

    public String getIsEmailVerified() {
        return IsEmailVerified;
    }
}
