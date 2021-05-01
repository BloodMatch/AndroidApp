package com.example.bloodmatch;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bloodmatch.data.RequestCollection;
import com.example.bloodmatch.model.Blood;
import com.example.bloodmatch.model.Motivation;
import com.example.bloodmatch.model.RequestModel;
import com.google.firebase.auth.FirebaseAuth;

public class MakeRequestActivity extends BaseActivity {

    private EditText whereEditText, motivationEditText;
    private RadioGroup typeRadioGroup, rhesusRadioGroup, benefitRadioGroup;
    private RadioButton typeRadioButton, rhesusRadioButton, benefitRadioButton;
    private ImageView locationImageView;
    private Button submitButton;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_make_request;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        submitButton = findViewById(R.id.submit_button);
        whereEditText = findViewById(R.id.where);
        locationImageView = findViewById(R.id.location);
        motivationEditText = findViewById(R.id.motivation_text);
        typeRadioGroup = findViewById(R.id.blood_group);
        rhesusRadioGroup = findViewById(R.id.rhesus_signe);
        benefitRadioGroup = findViewById(R.id.benefit);

        submitButton.setOnClickListener(v->{
            String bloodType, bloodRhesus, benefit, where, motivation;
            int selectedID;
            selectedID = typeRadioGroup.getCheckedRadioButtonId();
            if( selectedID != -1 ){
                typeRadioButton = findViewById(selectedID);
                bloodType =  typeRadioButton.getText().toString();
            }else {
                bloodType = null;
                Toast.makeText(MakeRequestActivity.this, "Please choose your blood type!", Toast.LENGTH_SHORT).show();
                return ;
            }

            selectedID = rhesusRadioGroup.getCheckedRadioButtonId();
            if( selectedID != -1 ){
                rhesusRadioButton = findViewById(selectedID);
                bloodRhesus =  rhesusRadioButton.getText().toString();
            }else {
                bloodRhesus = null;
                Toast.makeText(MakeRequestActivity.this, "Please choose your blood rhesus!", Toast.LENGTH_SHORT).show();
                return ;
            }

            selectedID = benefitRadioGroup.getCheckedRadioButtonId();
            if( selectedID != -1 ){
                benefitRadioButton = findViewById(selectedID);
                benefit =  benefitRadioButton.getText().toString();
            }else {
                benefit = null;
                Toast.makeText(MakeRequestActivity.this, "Please tell Who will benefit!", Toast.LENGTH_SHORT).show();
                return ;
            }

            where = whereEditText.getText().toString();
            if( where.isEmpty() ){
                whereEditText.setError("Please answer the question!");
                return ;
            }

            motivation = motivationEditText.getText().toString();

            RequestModel requestModel = new RequestModel();
            Motivation motivationModel = new Motivation();
            Blood blood = new Blood();

            motivationModel.setText(motivation);

            blood.setType(bloodType);
            blood.setRhesus(Character.toString(bloodRhesus.charAt(2)));

            requestModel.setBlood(blood);
            requestModel.setBenefit(benefit);
            requestModel.setLocation(where);
            requestModel.setMotivation(motivationModel);

            RequestCollection.insertDocument(requestModel)
                .addOnSuccessListener(aVoid->{
                    Toast.makeText(MakeRequestActivity.this, "Request added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(aVoid->{
                    Toast.makeText(MakeRequestActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                });

        });


        locationImageView.setOnClickListener(v->{


        });


    }
}