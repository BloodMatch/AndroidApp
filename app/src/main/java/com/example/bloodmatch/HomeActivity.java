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
import com.example.bloodmatch.data.DonorFirebase;
import com.example.bloodmatch.data.UserFirebase;
import com.example.bloodmatch.model.DonorModel;
import com.example.bloodmatch.model.UserModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

// To have navbar the class should entend the BaseActivity abstract class
public class HomeActivity extends BaseActivity {

    private ImageView ivUser;
    private TextView tvUserName, tvUserBloodGroup;
    private FirebaseAuth mAuth;
    private DonorModel donor;
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
        DonorFirebase.selectDocument(user.getEmail())
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        donor = documentSnapshot.toObject(DonorModel.class);
                        tvUserBloodGroup.setText(donor.getBloodGroup());
                        tvUserName.setText(user.getDisplayName());
                        ivUser.setImageURI(user.getPhotoUrl());
                    }
                });

        displayImage();
    }

    /**
    * Should implement this method to have a navbar
    */
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    /// Todo : should be in the Profile activity 
    public void updatePhotoUrl(Uri uri){
        UserFirebase.updatePhotoUrl(uri)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
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