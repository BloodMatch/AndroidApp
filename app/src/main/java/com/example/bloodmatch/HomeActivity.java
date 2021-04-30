package com.example.bloodmatch;

import androidx.annotation.NonNull;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.example.bloodmatch.data.DonorCollection;
import com.example.bloodmatch.data.UserAccount;
import com.example.bloodmatch.model.DonorModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

// To have navbar the class should entend the BaseActivity abstract class
public class HomeActivity extends BaseActivity {

    private ImageView ivUser;
    private TextView tvUserName, tvUserBloodGroup;
    private FirebaseAuth mAuth;
    private DonorModel donor;

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
        tvUserName.setText(user.getDisplayName());
        displayImage(user.getPhotoUrl());

        // Get user Document
        DonorCollection.selectDocument(user.getEmail())
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        donor = documentSnapshot.toObject(DonorModel.class);
                        tvUserBloodGroup.setText(donor.getBloodGroup());
                    }
                });
    }

    /// Todo : should be in the Profile activity 
    public void updatePhotoUrl(Uri uri){
        UserAccount.updatePhotoUrl(uri)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(HomeActivity.this, "User Image Updated", Toast.LENGTH_SHORT).show();
                    ivUser.setImageURI(user.getPhotoUrl());
                }
            }
        });
    }

    /**
     * Display the image to view element
     */
    private void displayImage(Uri imgUri){
        if(imgUri !=null){
            Glide.with(this)
                    .load(imgUri)
                    .into(ivUser);
        }
    }
}