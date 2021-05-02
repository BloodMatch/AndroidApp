package com.example.bloodmatch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.bloodmatch.data.DonorCollection;
import com.example.bloodmatch.model.DonorModel;
import com.google.firebase.auth.FirebaseAuth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DonorProfileActivity extends BaseActivity {

    private TextView displayNameTextView, donationsTextView, unitsTextView, bloodTextView;
    private Button  phoneCallButton, textMessageButton, whatsAppButton, checkInButton;
    private ImageView pictureImageView;
    private DonorModel donor;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_donor_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String email = "aubbenyas717@gmail.com";
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
        DonorCollection.selectDocument(email).addOnSuccessListener( documentSnapshot -> {
            donor = documentSnapshot.toObject(DonorModel.class);
            displayNameTextView.setText(donor.getDisplayName());

            donationsTextView.setText(String.valueOf( donor.getDonation().getFrequency().toString()));
            unitsTextView.setText(String.valueOf(donor.getDonation().getQuantity().toString()));
            bloodTextView.setText(donor.getBlood().toString());


            Uri imgUri = Uri.parse(donor.getPhotoUrl());
            if( imgUri !=null){
                Glide.with(DonorProfileActivity.this)
                        .load(imgUri)
                        .into(pictureImageView);
            }
        });

        phoneCallButton.setOnClickListener( v->{
            Toast.makeText(DonorProfileActivity.this, "Start Phone Call", Toast.LENGTH_SHORT).show();
            phoneCall();
        });

        textMessageButton.setOnClickListener(v->{
            Toast.makeText(DonorProfileActivity.this, "Write Text Message", Toast.LENGTH_SHORT).show();
            textMessage();
        });

        whatsAppButton.setOnClickListener(v->{
            Toast.makeText(DonorProfileActivity.this, "Open WhatsApp", Toast.LENGTH_SHORT).show();
            whatsApp2();
        });

    }

    private void phoneCall(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+donor.getPhoneNumber()));
        startActivity(intent);
    }

    private void textMessage(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:"+donor.getPhoneNumber()));
        intent.putExtra("sms_body",String.format("Hello %s,\nMy name is %s, And I need Blood!"
                ,donor.getDisplayName(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
        startActivity(intent);
    }

    private void whatsApp(){
        String message = String.format("Hello %s,\nMy name is %s, And I need Blood!"
                ,donor.getDisplayName(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        String url = null;
        try {
            url = "https://api.whatsapp.com/send"+donor.getPhoneNumber()+"?text="+ URLEncoder.encode(message,"UTF-8");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.whatsapp");
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void whatsApp2(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://api.whatsapp.com/send?phone="+donor.getPhoneNumber()));
        intent.setPackage("com.whatsapp");
        startActivity(intent);
    }


}