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
import com.example.bloodmatch.request.RequestManagerActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

// To have navbar the class should entend the BaseActivity abstract class
public class HomeActivity extends BaseActivity {

    private ImageView ivUser;
    private TextView tvUserName, tvUserBloodGroup;
    private DonorModel donor;
    private Button donorButton, recipientButton;
    private static FirebaseFirestore db;
    private static FirebaseAuth mAuth;

    static{
        db =  FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

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

        checkLogin(user);

        ivUser = findViewById(R.id.user_image);
        tvUserName = findViewById(R.id.user_name);
        tvUserBloodGroup = findViewById(R.id.user_bloodGroup);
        donorButton = findViewById(R.id.button_donor);
        recipientButton = findViewById(R.id.button_recipient);

        tvUserName.setText(user.getDisplayName());
        displayProfilePicture(user.getPhotoUrl());

        // Get user Document
        DonorCollection.selectDocument(user.getEmail())
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        donor = documentSnapshot.toObject(DonorModel.class);
                        tvUserBloodGroup.setText(donor.getBlood().toString());
                    }
                });

        donorButton.setOnClickListener(v->{
            Intent i = new Intent(HomeActivity.this, DonorProfileActivity.class);
            i.putExtra("DONOR_REFERENCE",
                    db.collection("donors").document("aubbenyas717@gmail.com").getPath());

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast.makeText(HomeActivity.this, "Donor Button Clicked", Toast.LENGTH_SHORT).show();
        });

        recipientButton.setOnClickListener(v->{
            Intent i = new Intent(HomeActivity.this, RequestManagerActivity.class);
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
    private void displayProfilePicture(Uri imgUri){
        if(imgUri !=null){
            Glide.with(this)
                    .load(imgUri)
                    .into(ivUser);
        }
    }
}