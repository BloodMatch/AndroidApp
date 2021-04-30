package com.example.bloodmatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.bloodmatch.data.DonorFirebase;
import com.example.bloodmatch.data.UserFirebase;
import com.example.bloodmatch.model.DonorModel;
import com.example.bloodmatch.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "Register";
    private int stepIndex = 1;
    private EditText
            fullNameEditText , emailEditText, passwordEditText, confirmPasswordEditText,
            cinEditText, birthDateEditText , cityEditText, zipCodeEditText,
            firstTimeEditText, lastTimeEditText, frequencyEditText, quantityEditText;
    private ProgressBar loadingProgressBar;
    private RadioGroup genderRadioGroup;
    private RadioButton genderSelectedButton;
    private TextView stepIndexTextView;
    private FirebaseAuth mAuth;
    private ViewFlipper viewFlipper;

    private View.OnClickListener stepOneActionListener, stepTwoActionListener, stepThreeActionListener;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        viewFlipper = findViewById(R.id.registration_form);
        stepIndexTextView = findViewById(R.id.stepIndex);
        continueButton = findViewById(R.id.button_continue);
        loadingProgressBar = findViewById(R.id.loading);

        fullNameEditText = findViewById(R.id.displayName);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);

        cinEditText = findViewById(R.id.cin);
        genderRadioGroup = findViewById(R.id.gender);
        birthDateEditText = findViewById(R.id.birthDate);
        cityEditText = findViewById(R.id.city);
        zipCodeEditText = findViewById(R.id.zipCode);

        firstTimeEditText = findViewById(R.id.firstTime);
        lastTimeEditText =findViewById(R.id.lastTime);
        frequencyEditText = findViewById(R.id.frequency);
        quantityEditText = findViewById(R.id.quantity);

        DonorModel donor = new DonorModel();
        UserModel user = new UserModel();

        stepOneActionListener = v -> {
            String fullName, email, password, confirmPassword;
            fullName = fullNameEditText.getText().toString();
            if( fullName.isEmpty() ){
                fullNameEditText.setError("Please enter your full name !");
                return;
            }

            email = emailEditText.getText().toString();
            if( email.isEmpty() ){
                emailEditText.setError("Email cannot be empty !");
                return;
            }

            if( !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailEditText.setError("Enter a valid email !");
                return;
            }

            password = passwordEditText.getText().toString();
            if( password.isEmpty() ){
                passwordEditText.setError("Password cannot be empty !");
                return;
            }

            if( password.length() < 6 ){
                passwordEditText.setError("The password must be at least 6 characters long !");
                return;
            }

            confirmPassword = confirmPasswordEditText.getText().toString();
            if( confirmPassword.isEmpty() ){
                confirmPasswordEditText.setError("Confirm password cannot be empty !");
                return;
            }

            if( !confirmPassword.equals(password) ){
                confirmPasswordEditText.setError("The confirm password confirmation does not match !");
                return;
            }

            user.setDisplayName(fullName);
            user.setEmail(email);
            user.setPassword(password);

            //update onclick listener 1->2
            continueButton.setOnClickListener(stepTwoActionListener);
            nextStep();
        };

        stepTwoActionListener = v -> {
            String cin, gender, birthDate, city;
            String szipCode;
            Integer zipCode;
            cin = cinEditText.getText().toString();
            if( cin.isEmpty() ){
                cinEditText.setError("Please enter your card ID !");
                return;
            }

            int selectedID = genderRadioGroup.getCheckedRadioButtonId();
            if( selectedID != -1 ) {
                genderSelectedButton = findViewById(selectedID);
                gender = genderSelectedButton.getText().toString();
            }else{
                gender = null;
            }

            birthDate = birthDateEditText.getText().toString();
            if( birthDate.isEmpty() ){
                birthDateEditText.setError("Please enter your birth date !");
                return;
            }

            city = cityEditText.getText().toString();
            if( city.isEmpty() ){
                cityEditText.setError("Please enter your city !");
                return;
            }

            szipCode = zipCodeEditText.getText().toString();

            if( szipCode.isEmpty() ){
                zipCode = null;
            }else{
                zipCode = Integer.parseInt(szipCode);
            }

            donor.setCin(cin);
            donor.setGender(gender);
            donor.setBirthDate(birthDate);
            donor.setCity(city);
            donor.setZipCode(zipCode);

            //update onclick listener 2->3
            continueButton.setText(R.string.create_button_text);
            continueButton.setOnClickListener(stepThreeActionListener);
            nextStep();
        };
        stepThreeActionListener = v -> {
            String firstTime, lastTime;
            String sfrequency, squantity;
            Integer frequency, quantity;
            firstTime = firstTimeEditText.getText().toString();
            lastTime = lastTimeEditText.getText().toString();
            sfrequency = frequencyEditText.getText().toString();
            squantity = quantityEditText.getText().toString();

            if( sfrequency.isEmpty() ){
                frequency = null;
            }else{
                frequency = Integer.parseInt(sfrequency);
            }

            if( squantity.isEmpty() ){
                quantity = null;
            }else{
                quantity = Integer.parseInt(squantity);
            }

            donor.setFirstTime(firstTime);
            donor.setLastTime(lastTime);
            donor.setFrequency(frequency);
            donor.setQuantity(quantity);

            loadingProgressBar.setVisibility(View.VISIBLE);

            DonorFirebase donorFirebase = new DonorFirebase(donor);
            UserFirebase userFirebase = new UserFirebase((user));

            userFirebase.createAccount().addOnCompleteListener(RegisterActivity.this, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                    UserFirebase.sendEmailVerification();
                    donorFirebase.insertDocument(user.getEmail()).addOnSuccessListener(aVoid -> {
                        Toast.makeText(RegisterActivity.this, "User document created", Toast.LENGTH_SHORT).show();
                        // set default image profile
                        setupUser(user.getDisplayName());
                    });

                } else {
                    Log.d(TAG, "createAccount:failure");
                }
                loadingProgressBar.setVisibility(View.GONE);
            });
        };

        continueButton.setOnClickListener(stepOneActionListener);
    }

    public void nextStep(){
        viewFlipper.showNext();     stepIndex++;
        stepIndexTextView.setText("Step 0"+stepIndex);
    }

    public void endActivity(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    /**
     * Set up user default image and display name
     * */
    private void setupUser(String displayName){
        UserFirebase.getPhotoUrl("default-avatar.png")
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(RegisterActivity.this, "Image url retreved", Toast.LENGTH_SHORT).show();
                        UserFirebase.updateProfile(displayName, uri)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(RegisterActivity.this, "Image updated", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                });
                    }
                });
    }
}