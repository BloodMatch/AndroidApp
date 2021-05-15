package com.example.bloodmatch.request;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.bloodmatch.BaseActivity;
import com.example.bloodmatch.R;
import com.example.bloodmatch.model.RequestModel;
import com.example.bloodmatch.request.form.MakeRequestFragment;
import com.example.bloodmatch.request.list.DonorsListFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.annotation.Nullable;

public class RequestManagerActivity extends BaseActivity {

    private static FirebaseFirestore db;
    private static FirebaseAuth mAuth;
    private RequestModel myRequest;

    static{
        db =  FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public void setRequest(RequestModel request) {
        this.myRequest = request;
    }
    public RequestModel getRequest() {
        return myRequest;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_request_manager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db.collection("requests")
            .whereEqualTo("inNeed", db.collection("donors").document(mAuth.getCurrentUser().getEmail()))
            //.whereEqualTo("active", true);
            //.limit(1)
            .get()
                .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot result = task.getResult();
                    //List<RequestModel> listRequest = result.toObjects(RequestModel.class);
                    if(result.size() <1 ) {  // no request // sir 7ta tji
                        openFragment(new MakeRequestFragment());
                    } else { // ghadi ghadi, ddi m3ak hadi
                        DocumentSnapshot document = result.getDocuments().get(0);
                        RequestModel request = document.toObject(RequestModel.class);
                        request.id(document.getId());
                        setRequest(request);
                        openFragment(new DonorsListFragment());
                    }
                }
            });

    }

    public void openFragment(Fragment fragment ) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_request, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
    public void openFragment(Fragment fragment, Boolean addToBackStack ) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_request, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}