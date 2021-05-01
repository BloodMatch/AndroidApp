package com.example.bloodmatch.data;

import com.example.bloodmatch.model.DonorModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DonorCollection {
    private static FirebaseFirestore db;
    private static final String collectionName;

    static {
        db = FirebaseFirestore.getInstance();
        collectionName = "donors";
    }

    public static Task <DocumentSnapshot> selectDocument(String email){
        return  db.collection(collectionName).document(email)
                .get();
    }

    public static Task <DocumentSnapshot> selectDocumentByRefrence(DocumentReference docRef){
        return docRef.get();
    }

    public static Task<Void> insertDocument(String email, DonorModel donor){
        return  db.collection(collectionName).document(email)
                .set(donor);
    }
}
