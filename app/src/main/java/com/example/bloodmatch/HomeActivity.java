package com.example.bloodmatch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bloodmatch.data.DonorCollection;
import com.example.bloodmatch.data.UserAccount;
import com.example.bloodmatch.model.DonorModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

// To have navbar the class should entend the BaseActivity abstract class
public class HomeActivity extends BaseActivity {

    private ImageView ivUser;
    private TextView tvUserName, tvUserBloodGroup;
    private FirebaseAuth mAuth;
    private DonorModel donor;
    private Button donorButton, recipientButton;
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    /**
     * Should implement this method to have a navbar
     */
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

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
        donorButton = findViewById(R.id.button_donor);
        recipientButton = findViewById(R.id.button_recipient);

        displayProfilePicture();

        // Get user Document
        DonorCollection.selectDocument(user.getEmail())
                .addOnSuccessListener(documentSnapshot -> {
                    donor = documentSnapshot.toObject(DonorModel.class);
                    tvUserBloodGroup.setText(donor.getBlood().toString());
                    tvUserName.setText(user.getDisplayName());
                });

        donorButton.setOnClickListener(v->{
            /*Intent i = new Intent(HomeActivity.this, MakeRequestActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);*/
            Toast.makeText(HomeActivity.this, "Donor Button Clicked", Toast.LENGTH_SHORT).show();
        });

        recipientButton.setOnClickListener(v->{
            Intent i = new Intent(HomeActivity.this, MakeRequestActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });
    }

    /// Todo : should be in the Profile activity 
    public void updatePhotoUrl(Uri uri){
        UserAccount.updatePhotoUrl(uri)
        .addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser user = mAuth.getCurrentUser();
                Toast.makeText(HomeActivity.this, "User Image Updated", Toast.LENGTH_SHORT).show();
                ivUser.setImageURI(user.getPhotoUrl());
            }
        });
    }

    /**
     * Display the image to view element
     */
    private void displayProfilePicture(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user.getPhotoUrl() !=null){
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(ivUser);
        }
    }


}