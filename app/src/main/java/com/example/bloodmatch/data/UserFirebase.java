package com.example.bloodmatch.data;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.bloodmatch.model.DonorModel;
import com.example.bloodmatch.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.Executor;

public class UserFirebase {
    protected  UserModel userModel;
    private static FirebaseAuth  mAuth;

    static {
        mAuth = FirebaseAuth.getInstance();
    }

    public UserFirebase(){}

    public UserFirebase(UserModel userModel){
        this.userModel = userModel;
    }

    public void updateProfile() {
        // [START update_profile]
        FirebaseUser user = mAuth.getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userModel.getDisplayName())
                .setPhotoUri(Uri.parse("donorModel.getPhotoUrl()"))
                .build();

        //user.updatePhoneNumber(donorModel.getPhoneNumber());
        user.updateProfile(profileUpdates);

        // [END update_profile]
    }

    public Task<Void> updatePassword() {
        // [START update_password]
        FirebaseUser user = mAuth.getCurrentUser();

        return user.updatePassword(userModel.getPassword());
        // [END update_password]
    }

    public static Task<Void> sendEmailVerification() {
        // [START send_email_verification]
        FirebaseUser user = mAuth.getCurrentUser();

        return user.sendEmailVerification();
        // [END send_email_verification]
    }

    public Task<Void> sendPasswordReset() {
        // [START send_password_reset]

        return mAuth.sendPasswordResetEmail(userModel.getEmail());
        // [END send_password_reset]
    }

    public Task<AuthResult> createAccount() {
        // [START create_user_with_email]
        return mAuth.createUserWithEmailAndPassword(userModel.getEmail(), userModel.getPassword());

        // [END create_user_with_email]
    }

    public Task<AuthResult> signIn() {
        // [START sign_in_with_email]
        return mAuth.signInWithEmailAndPassword(userModel.getEmail(), userModel.getPassword());
        // [END sign_in_with_email]
    }

}
