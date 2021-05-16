package com.example.bloodmatch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.example.bloodmatch.data.DonorCollection;
import com.example.bloodmatch.data.UserAccount;
import com.example.bloodmatch.model.DonorModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

// To have navbar the class should entend the BaseActivity abstract class
public class HomeActivity extends BaseActivity {

    private ImageView ivUser;
    private TextView tvUserName, tvUserBloodGroup;
    private FirebaseAuth mAuth;
    private DonorModel donor;
    private Button donorButton, recipientButton;

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
            Intent i = new Intent(HomeActivity.this, RequestListActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        recipientButton.setOnClickListener(v->{
            Intent i = new Intent(HomeActivity.this, MakeRequestActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
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