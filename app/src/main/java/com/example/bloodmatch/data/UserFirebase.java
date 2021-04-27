package com.example.bloodmatch.data;

import android.net.Uri;

import com.example.bloodmatch.model.UserModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserFirebase {
    protected  UserModel userModel;
    private static FirebaseAuth  mAuth;
    private static FirebaseStorage storage;
    static {
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public UserFirebase(){}

    public UserFirebase(UserModel userModel){
        this.userModel = userModel;
    }


    /**
     * Get image Uri from FirebaseStorage
     * */
    public static Task<Uri> getPhotoUrl(String endPoint){
        FirebaseUser user = mAuth.getCurrentUser();
        StorageReference profileImageRef = storage.getReference().child("profile/"+endPoint);

        return profileImageRef.getDownloadUrl();
    }

    public void updateDisplayName() {
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userModel.getDisplayName())
                .build();

        user.updateProfile(profileUpdates);
    }

    /**
     * Set user profile image
     * */
    public static Task<Void> updatePhotoUrl(Uri uri){
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        return user.updateProfile(profileUpdates);
    }

    public Task<Void> updatePassword() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user.updatePassword(userModel.getPassword());
    }

    public static Task<Void> sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user.sendEmailVerification();
    }

    public Task<Void> sendPasswordReset() {
        return mAuth.sendPasswordResetEmail(userModel.getEmail());
    }

    public Task<AuthResult> createAccount() {
        return mAuth.createUserWithEmailAndPassword(userModel.getEmail(), userModel.getPassword());
    }

    public static Task<AuthResult> signIn(String email, String password) {
        return mAuth.signInWithEmailAndPassword(email, password);
    }

}
