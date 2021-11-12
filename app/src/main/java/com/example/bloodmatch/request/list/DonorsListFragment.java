package com.example.bloodmatch.request.list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bloodmatch.DonorProfileActivity;
import com.example.bloodmatch.R;
import com.example.bloodmatch.model.DonorModel;
import com.example.bloodmatch.model.RequestModel;
import com.example.bloodmatch.request.RequestManagerActivity;
import com.example.bloodmatch.request.page.DonorsPagerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DonorsListFragment extends Fragment {

    private static FirebaseFirestore db;
    private static FirebaseAuth mAuth;
    private RequestModel myRequest;
    private TextView countDonorsText;
    private List<DonorModel> listDonors;
    private ListView listView;
    private  int i , len;

    static{
        db =  FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }
    public DonorsListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donors_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        countDonorsText = (TextView)view.findViewById( R.id.count_donors);
        listView = (ListView) view.findViewById( R.id.container_list_donors);
        myRequest = ((RequestManagerActivity)getActivity()).getRequest();

        Button findDonorsButton = (Button) view.findViewById(R.id.find_donors);
        findDonorsButton.setOnClickListener(v->{
            ((RequestManagerActivity)getActivity()).openFragment(new DonorsPagerFragment(), true);
        });

        listView.setOnItemClickListener((AdapterView.OnItemClickListener) (parent, v, position, id) -> {

            Intent i = new Intent(getActivity(), DonorProfileActivity.class);

            i.putExtra("DONOR_REFERENCE",
                    db.collection("donors").document(listDonors.get(position).id()).getPath()
            );

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast.makeText(getActivity(), "Position["+position+"] : "+listDonors.get(position).getDisplayName(), Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        listDonors = listDonors = new ArrayList<DonorModel>();
        i=0;
        len = myRequest.getDonors().size();
        for (DocumentReference document: myRequest.getDonors()) {
            i++;
            document.get().addOnCompleteListener(task ->  {
                if (task.isSuccessful()) {
                    DocumentSnapshot donorDocument = task.getResult();
                    if (donorDocument.exists()) {
                        DonorModel donor = donorDocument.toObject(DonorModel.class);
                        donor.id(donorDocument.getId());
                        listDonors.add((donor));
                    }
                }
                if( i == len ){
                    onListFilled();
                }
            });
        }
    }

    private void onListFilled(){
        countDonorsText.setText( String.valueOf( listDonors.size()));
        DonorListAdapter calendarListAdapter = new DonorListAdapter( getActivity(), listDonors);
        listView.setAdapter( calendarListAdapter );
    }

}