package com.example.bloodmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

public class RequestListActivity extends BaseActivity {

    private FirebaseAuth mAuth;

    private ListView lvrequest;
    private List<RequestModel> requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        lvrequest = findViewById(R.id.requests);
        RequestListAdapter adapter = new RequestListAdapter(this, requests);
        lvrequest.setAdapter(adapter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_request_list;
    }
}