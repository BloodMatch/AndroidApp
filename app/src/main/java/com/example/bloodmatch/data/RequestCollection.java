package com.example.bloodmatch.data;

import com.example.bloodmatch.model.DonorModel;
import com.example.bloodmatch.model.RequestModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestCollection {
    private static FirebaseAuth mAuth;
    private static FirebaseFirestore db;
    private static final String collectionName;

    static {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        collectionName = "requests";
    }

    public RequestCollection(){ }

    public static Task<DocumentSnapshot> selectDocument(String id){
        return  db.collection("donors").document(id)
                .get();
    }

    public static Task<DocumentReference> insertDocument(RequestModel requestModel){
        FirebaseUser user = mAuth.getCurrentUser();
        DocumentReference creator = db.collection("donors").document(user.getEmail());

        requestModel.setInNeed(creator);
        return db.collection(collectionName)
                .add(requestModel);
    }

}
