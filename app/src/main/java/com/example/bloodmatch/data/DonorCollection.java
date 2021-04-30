package com.example.bloodmatch.data;

import com.example.bloodmatch.model.DonorModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DonorCollection {
    protected  DonorModel donorModel;
    private static FirebaseFirestore db;
    private static String collectionName;

    static {
        db = FirebaseFirestore.getInstance();
        collectionName = "donors";
    }

    public DonorCollection(DonorModel donorModel){ this.donorModel = donorModel; }

    public DonorModel getDonorModel(){
        return donorModel;
    }

    public static Task <DocumentSnapshot> selectDocument(String email){
        return  db.collection("donors").document(email)
                .get();
    }

    public Task<Void> insertDocument(String email){
        return  db.collection("donors").document(email)
                .set(donorModel);
    }
}
