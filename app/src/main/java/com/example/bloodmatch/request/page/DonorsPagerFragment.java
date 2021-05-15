package com.example.bloodmatch.request.page;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bloodmatch.R;
import com.example.bloodmatch.model.DonorModel;
import com.example.bloodmatch.model.RequestModel;
import com.example.bloodmatch.request.RequestManagerActivity;
import com.example.bloodmatch.request.list.DonorListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DonorsPagerFragment extends Fragment {

    private ViewPager mViewPager;
    private List<DonorModel> listDonors;
    private static FirebaseFirestore db;
    private static FirebaseAuth mAuth;

    static{
        db =  FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }
    public DonorsPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donors_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db.collection("donors")
                .whereEqualTo("available" , true)
                .orderBy("displayName")
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                //listDonors = result.toObjects(DonorModel.class);
                listDonors = new ArrayList<DonorModel>();
                for (QueryDocumentSnapshot document: task.getResult()) {
                    DonorModel donor = document.toObject(DonorModel.class);
                    donor.id(document.getId());
                    listDonors.add((donor));
                }

                mViewPager = (ViewPager) view.findViewById(R.id.viewPagerMain);
                DonorPagerAdapter donorPagerAdapter = new DonorPagerAdapter( getContext(), listDonors);

                mViewPager.setAdapter( donorPagerAdapter );

            }else{
                String exceptionMessage = task.getException().getMessage();
                Toast.makeText(getActivity(), "Something went wrong!\n"+ exceptionMessage, Toast.LENGTH_SHORT).show();
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("firebase_error : ",exceptionMessage );
                clipboardManager.setPrimaryClip(clip);
            }
        });

        Button askForHelpButton = (Button) view.findViewById(R.id.ask_for_help);
        askForHelpButton.setOnClickListener(v->{
            Toast.makeText(getActivity(), "current "+String.valueOf(mViewPager.getCurrentItem()), Toast.LENGTH_SHORT).show();
            /*listDonors.remove(mViewPager.getCurrentItem());
            mViewPager.getAdapter().notifyDataSetChanged();*/
        });
    }

    public void askDonorForHelp(DonorModel donor){
        RequestModel myRequest = ((RequestManagerActivity)getActivity()).getRequest();
        myRequest.getDonors().add(db.collection("donors").document());

    }

    public void filterArrayDonor()
    {
        //this.listDonors
        // blood type
        // machine Learning (>50%)
    }
}