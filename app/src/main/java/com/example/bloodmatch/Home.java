package com.example.bloodmatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bloodmatch.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Home extends BaseActivity {

    private ImageView ivUser;
    private TextView tvUserName, tvUserBloodGroup;
    private FirebaseAuth mAuth;
    private User userObj;
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        checkLogin(user);

        ivUser = findViewById(R.id.user_image);
        tvUserName = findViewById(R.id.user_name);
        tvUserBloodGroup = findViewById(R.id.user_bloodGroup);

        // Get user Document
        db.collection("User")
                .document(user.getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        userObj = documentSnapshot.toObject(User.class);
                        tvUserBloodGroup.setText(userObj.getBloodGroup());
                        tvUserName.setText(user.getDisplayName());
                        ivUser.setImageURI(user.getPhotoUrl());
                    }
                });

        getDefaultProfileImage();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }


    /**
     *  Check if the user is logged in
     * */
    private void checkLogin(FirebaseUser user){
        if(user == null){
            startActivity(new Intent(Home.this, LoginActivity.class));
            finish();
        }
    }

    /**
     * Set user profile image
     * */
    private void setUserProfileUrl(Uri uri){
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Home.this, "User Image Updated", Toast.LENGTH_SHORT).show();
                    ivUser.setImageURI(user.getPhotoUrl());
                }
            }
        });
    }

    /**
     * Get image Uri from FirebaseStorage
     * */
    private void getDefaultProfileImage(){
        FirebaseUser user = mAuth.getCurrentUser();
        String userImage = user.getUid()+".jpg";
        StorageReference storageRef = storage.getReference();
        StorageReference profileImageRef = storageRef.child("profile");

        profileImageRef.child(userImage)
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // just once
                        // setUserProfileUrl(uri);
                        
                        // display Image
                        displayImage();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Home.this, "Failed to getUri default", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Display the image to view element
     */
    private void displayImage(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user.getPhotoUrl() !=null){
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(ivUser);

        }
    }


}