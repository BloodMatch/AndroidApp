package com.example.bloodmatch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.bloodmatch.adapter.RequestListAdapter;
import com.example.bloodmatch.data.DonorCollection;
import com.example.bloodmatch.data.RequestCollection;
import com.example.bloodmatch.model.DonorModel;
import com.example.bloodmatch.model.RequestModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RequestListActivity extends BaseActivity {

    private static final String TAG = "RequestList";
    private FirebaseAuth mAuth;
    private DonorModel donor;
    private RecyclerView rvrequest;
    private List<RequestModel> requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        requests = new ArrayList<>();

        rvrequest = findViewById(R.id.recyleView);

        synchronized(this){
            DonorCollection.selectDocument(user.getEmail())
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            donor = documentSnapshot.toObject(DonorModel.class);
                            // array request references not empty
                            if(donor.getRequests() != null){
                                Toast.makeText(RequestListActivity.this, "Nice "+donor.getRequests().size(), Toast.LENGTH_SHORT).show();
                                for(DocumentReference ref : donor.getRequests()) {
                                    getRequest(ref);
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RequestListActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        setAdapter();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_request_list;
    }

    private void getRequest(DocumentReference ref){
        RequestCollection.selectDocumentByRefrence(ref)
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        RequestModel request = documentSnapshot.toObject(RequestModel.class);
                        if(request != null){
                            appendRequest(request);
                        }else{
                            Toast.makeText(RequestListActivity.this, "null request", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void appendRequest(RequestModel request){
        requests.add(request);
    }

    private void setAdapter(){
        RequestListAdapter adapter = new RequestListAdapter(this, requests);
        rvrequest.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvrequest.setLayoutManager(linearLayoutManager);
    }
}