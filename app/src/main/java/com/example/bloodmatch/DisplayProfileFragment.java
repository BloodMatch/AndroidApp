package com.example.bloodmatch;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.bloodmatch.model.DonorModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DisplayProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DonorModel donor;

    private TextView tvemail, tvcin, tvgender, tvbirthDate, tvcity;
    private Button updateButton;

    private View.OnClickListener changeFaragment;

    public DisplayProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_display_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser user = mAuth.getCurrentUser();

        tvcin = getView().findViewById(R.id.tvcin);
        tvemail = getView().findViewById(R.id.tvemail);
        tvgender = getView().findViewById(R.id.tvgender);
        tvbirthDate = getView().findViewById(R.id.tvbirthDate);
        tvcity = getView().findViewById(R.id.tvcity);
        updateButton = getView().findViewById(R.id.button_update);

        changeFaragment = v -> {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            UpdateProfileFragment updateFragment = new UpdateProfileFragment();
            updateFragment.setDonor(donor);
            fragmentTransaction.replace(R.id.fl_fragment, updateFragment, "updateProfile");
            fragmentTransaction.commit();
        };

        tvemail.setText(user.getEmail());
        tvcin.setText(donor.getCin());
        tvbirthDate.setText(donor.getBirthDate());
        tvcity.setText(donor.getCity());
        tvgender.setText(donor.getGender());

        updateButton.setOnClickListener(changeFaragment);
    }
}