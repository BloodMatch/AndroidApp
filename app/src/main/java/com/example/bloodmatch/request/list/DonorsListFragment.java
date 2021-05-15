package com.example.bloodmatch.request.list;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodmatch.DonorProfileActivity;
import com.example.bloodmatch.HomeActivity;
import com.example.bloodmatch.R;
import com.example.bloodmatch.model.DonorModel;
import com.example.bloodmatch.model.RequestModel;
import com.example.bloodmatch.request.RequestManagerActivity;
import com.example.bloodmatch.request.page.DonorsPagerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DonorsListFragment extends Fragment {

    private static FirebaseFirestore db;
    private static FirebaseAuth mAuth;
    private RequestModel myRequest;
    private List<DonorModel> listDonors;
    private ListView listView;

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
        TextView countDonorsText = (TextView)view.findViewById( R.id.count_donors);
        listView = (ListView) view.findViewById( R.id.container_list_donors);

        db.collection("donors")
                .whereEqualTo("available" , true)
                .orderBy("displayName")
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot result  = task.getResult();

                listDonors = listDonors = new ArrayList<DonorModel>();
                for (QueryDocumentSnapshot document: task.getResult()) {
                    DonorModel donor = document.toObject(DonorModel.class);
                    donor.id(document.getId());
                    listDonors.add((donor));
                }

                countDonorsText.setText( String.valueOf( listDonors.size()));
                DonorListAdapter calendarListAdapter = new DonorListAdapter( getActivity(), listDonors);

                listView.setAdapter( calendarListAdapter );

            }else{
                String exceptionMessage = task.getException().getMessage();
                Toast.makeText(getActivity(), "Something went wrong!\n"+ exceptionMessage, Toast.LENGTH_SHORT).show();
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("firebase_error : ",exceptionMessage );
                clipboardManager.setPrimaryClip(clip);
            }
        });

        listView.setOnItemClickListener((AdapterView.OnItemClickListener) (parent, v, position, id) -> {
            //Toast.makeText(getActivity(),"Position["+position+"] : "+listDonors.get(position).getDisplayName(),Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getActivity(), DonorProfileActivity.class);
            i.putExtra("DONOR_EMAIL", listDonors.get(position).id());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast.makeText(getActivity(), "Position["+position+"] : "+listDonors.get(position).getDisplayName(), Toast.LENGTH_SHORT).show();
        });


        Button findDonorsButton = (Button) view.findViewById(R.id.find_donors);
        findDonorsButton.setOnClickListener(v->{
            ((RequestManagerActivity)getActivity()).openFragment(new DonorsPagerFragment(), true);
        });
    }

}