package com.example.bloodmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bloodmatch.data.DonorCollection;
import com.example.bloodmatch.model.DonorModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

public class DonorProfileActivity extends AppCompatActivity {

    private TextView displayNameTextView, donationsTextView, unitsTextView, bloodTextView;
    private Button  phoneCallButton, textMessageButton, whatsAppButton, checkInButton;
    private ImageView pictureImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_profile);
        String email = "aubbneyas717@gmail.com";
        displayNameTextView = findViewById(R.id.displayName);
        donationsTextView = findViewById(R.id.donations);
        unitsTextView = findViewById(R.id.units);
        bloodTextView = findViewById(R.id.blood);
        pictureImageView = findViewById(R.id.picture);
        phoneCallButton = findViewById(R.id.phone_call);
        textMessageButton = findViewById(R.id.text_message);
        whatsAppButton = findViewById(R.id.whatsApp);
        checkInButton = findViewById(R.id.check_in);

        // Get user Document
        DonorCollection.selectDocument(email)
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        DonorModel donor = documentSnapshot.toObject(DonorModel.class);

                        donationsTextView.setText(String.valueOf( donor.getDonation().getFrequency().toString()));
                        unitsTextView.setText(String.valueOf(donor.getDonation().getQuantity().toString()));
                        bloodTextView.setText(donor.getBlood().toString());
                        String imgUri = donor.getPhotoUrli();
                        if( imgUri !=null){
                            Glide.with(this)
                                    .load(imgUri)
                                    .into(pictureImageView);
                        }
                    }
                });
    }
}