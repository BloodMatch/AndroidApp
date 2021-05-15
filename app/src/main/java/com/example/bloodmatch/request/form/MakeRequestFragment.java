package com.example.bloodmatch.request.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bloodmatch.R;
import com.example.bloodmatch.data.RequestCollection;
import com.example.bloodmatch.model.Blood;
import com.example.bloodmatch.model.Motivation;
import com.example.bloodmatch.model.RequestModel;
import com.example.bloodmatch.request.RequestManagerActivity;
import com.example.bloodmatch.request.list.DonorsListFragment;

public class MakeRequestFragment extends Fragment {

    private EditText whereEditText, motivationEditText;
    private RadioGroup typeRadioGroup, rhesusRadioGroup, benefitRadioGroup;
    private RadioButton typeRadioButton, rhesusRadioButton, benefitRadioButton;
    private ImageView locationImageView;
    private Button submitButton;

    public MakeRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_make_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        submitButton = view.findViewById(R.id.submit_button);
        whereEditText = view.findViewById(R.id.where);
        locationImageView = view.findViewById(R.id.location);
        motivationEditText = view.findViewById(R.id.motivation_text);
        typeRadioGroup = view.findViewById(R.id.blood_group);
        rhesusRadioGroup = view.findViewById(R.id.rhesus_signe);
        benefitRadioGroup = view.findViewById(R.id.benefit);

        submitButton.setOnClickListener(v->{
            String bloodType, bloodRhesus, benefit, where, motivation;
            int selectedID;
            selectedID = typeRadioGroup.getCheckedRadioButtonId();
            if( selectedID != -1 ){
                typeRadioButton = view.findViewById(selectedID);
                bloodType =  typeRadioButton.getText().toString();
            }else {
                bloodType = null;
                Toast.makeText(getActivity(), "Please choose your blood type!", Toast.LENGTH_SHORT).show();
                return ;
            }

            selectedID = rhesusRadioGroup.getCheckedRadioButtonId();
            if( selectedID != -1 ){
                rhesusRadioButton = view.findViewById(selectedID);
                bloodRhesus =  rhesusRadioButton.getText().toString();
            }else {
                bloodRhesus = null;
                Toast.makeText(getActivity(), "Please choose your blood rhesus!", Toast.LENGTH_SHORT).show();
                return ;
            }

            selectedID = benefitRadioGroup.getCheckedRadioButtonId();
            if( selectedID != -1 ){
                benefitRadioButton = view.findViewById(selectedID);
                benefit =  benefitRadioButton.getText().toString();
            }else {
                benefit = null;
                Toast.makeText(getActivity(), "Please tell Who will benefit!", Toast.LENGTH_SHORT).show();
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

                        ((RequestManagerActivity)getActivity()).setRequest(requestModel);
                        ((RequestManagerActivity)getActivity()).openFragment(new DonorsListFragment());
                        Toast.makeText(getActivity(), "Request added successfully", Toast.LENGTH_SHORT).show();
                        //getActivity().finish();
                    }).addOnFailureListener(aVoid->{
                Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            });

        });

    }
}