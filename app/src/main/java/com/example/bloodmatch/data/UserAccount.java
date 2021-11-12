package com.example.bloodmatch.data;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;

public class UserAccount {
    private static FirebaseAuth  mAuth;
    private static FirebaseStorage storage;
    static {
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public UserAccount(){}

    /**
     * Get image Uri from FirebaseStorage
     * */
    public static Task<Uri> getPhotoUrl(String endPoint){
        StorageReference profileImageRef = storage.getReference().child("profile/"+endPoint);

        return profileImageRef.getDownloadUrl();
    }

    /**
     * Upload user photo from local file
     */
    public static UploadTask uploadPhoto(Uri photoUri){
        FirebaseUser user = mAuth.getCurrentUser();
        StorageReference ref = storage.getReference().child("profile/"+user.getUid()+".jpeg");

        return ref.putFile(photoUri);
    }

    /**
     * Upload user photo from data memory
     */
    public static UploadTask uploadPhoto(ByteArrayOutputStream baos){
        FirebaseUser user = mAuth.getCurrentUser();
        StorageReference ref = storage.getReference().child("profile/"+user.getUid()+".jpeg");
        byte[] data = baos.toByteArray();

        return ref.putBytes(data);
    }

    /**
     * Set user display name
     * */
    public static Task<Void> updateDisplayName(String displayName) {
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build();
        return user.updateProfile(profileUpdates);
    }


    /**
     * Set user profile image
     * */
    public static Task<Void> updatePhotoUrl(Uri uri){
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        DonorCollection.updatePhotoUrl(uri.toString());
        return user.updateProfile(profileUpdates);
    }

    public static Task<Void> updatePassword(String newPassword) {
        FirebaseUser user = mAuth.getCurrentUser();
        return user.updatePassword(newPassword);
    }

    /**
     * Set user photo and display Name in one request
     * */
    public static Task<Void> updateProfile(String displayName, Uri uri){
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .setDisplayName(displayName)
                .build();
        return user.updateProfile(profileUpdates);
    }

    public static Task<Void> sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user.sendEmailVerification();
    }

    public static Task<Void> sendPasswordReset(String email) {
        return mAuth.sendPasswordResetEmail(email);
    }

    public static Task<AuthResult> createAccount(String email, String password) {
        return mAuth.createUserWithEmailAndPassword(email, password);
    }

    public static Task<AuthResult> signIn(String email, String password) {
        return mAuth.signInWithEmailAndPassword(email, password);
    }

}
