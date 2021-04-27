package com.example.bloodmatch.model;

import android.net.Uri;

public class UserModel {
    private String DisplayName, Email, IsEmailVerified,  PhoneNumber, Password;
    private Uri  PhotoUrl;

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
        PhotoUrl = PhotoUrl;
    }

    public Uri getPhotoUrl() {
        return PhotoUrl;
    }

    public void setIsEmailVerified(String isEmailVerified) {
        IsEmailVerified = isEmailVerified;
    }

    public String getIsEmailVerified() {
        return IsEmailVerified;
    }
}
