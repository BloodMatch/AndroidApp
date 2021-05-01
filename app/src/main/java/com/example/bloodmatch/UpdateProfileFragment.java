package com.example.bloodmatch;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bloodmatch.data.DonorCollection;
import com.example.bloodmatch.model.DonorModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    private DonorModel donor;
    private Button saveButton, cancelButton;
    private EditText  edcin, edbirthday, edcity, edphone;
    private Spinner spinnerBlood;
    private View.OnClickListener updateDonor, cancel;

    public UpdateProfileFragment() {
        // Required empty public constructor
    }

    public void setDonor(DonorModel donor){
        this.donor = donor;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser user = mAuth.getCurrentUser();

        saveButton = getView().findViewById(R.id.button_save);
        cancelButton = getView().findViewById(R.id.button_cancel);
        edcin = getView().findViewById(R.id.edcin);
        edbirthday = getView().findViewById(R.id.edbirthday);
        edcity = getView().findViewById(R.id.edcity);
        edphone = getView().findViewById(R.id.edphone);

        spinnerBlood = getView().findViewById(R.id.blood_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.bloodGroups, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int initialValue = adapter.getPosition(donor.getBlood().toString());
        spinnerBlood.setAdapter(adapter);
        spinnerBlood.setSelection(initialValue);
        spinnerBlood.setOnItemSelectedListener(this);

        bindInputs(user);

        updateDonor = v -> {
            String  city, birthday, cin, phone;

            city = edcity.getText().toString();
            if( city.isEmpty() ){
                edcity.setError("Please enter your city");
                return;
            }

            birthday = edbirthday.getText().toString();
            if( birthday.isEmpty() ){
                edbirthday.setError("Please enter your Birthday");
                return;
            }

            cin = edcin.getText().toString();
            if( cin.isEmpty() ){
                edcin.setError("Please enter your Cin");
                return;
            }

            phone = edphone.getText().toString();
            if( phone.isEmpty() ){
                edphone.setError("Please enter your Phone number");
                return;
            }

            donor.setCin(cin);
            donor.setBirthDate(birthday);
            donor.setCity(city);
            donor.setPhoneNumber(phone);

            DonorCollection.insertDocument(user.getEmail(), donor)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getActivity(), "Profile updated.", Toast.LENGTH_SHORT).show();
                        switchFragment(donor);
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "User email already exits !", Toast.LENGTH_SHORT).show();
                }
            });
        };

        cancel = v -> {
            bindInputs(user);
            switchFragment(donor);
        };

        cancelButton.setOnClickListener(cancel);
        saveButton.setOnClickListener(updateDonor);
    }

    private void bindInputs(FirebaseUser user){
        edbirthday.setText(donor.getBirthDate());
        edcin.setText(donor.getCin());
        edcity.setText(donor.getCity());
        edphone.setText(donor.getPhoneNumber());
    }

    private void switchFragment(DonorModel donor){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        DisplayProfileFragment displayFragment = new DisplayProfileFragment();
        displayFragment.setDonor(donor);
        fragmentTransaction.replace(R.id.fl_fragment, displayFragment, "displayProfile");
        fragmentTransaction.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String bloodType = parent.getItemAtPosition(position).toString();
        donor.getBlood().setBloodGroup(bloodType);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}