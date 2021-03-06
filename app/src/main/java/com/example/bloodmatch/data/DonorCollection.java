package com.example.bloodmatch.data;

import android.util.Log;

import com.example.bloodmatch.model.DonorModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DonorCollection {
    private static FirebaseAuth mAuth;
    private static FirebaseFirestore db;
    private static final String collectionName;

    static {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        collectionName = "donors";
    }

    public static Task <DocumentSnapshot> selectDocument(String email){
        Log.d("collection;email : ", email);
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

    public static Task<Void> insertDocument( DonorModel donor){
        return insertDocument( mAuth.getCurrentUser().getEmail(), donor);
    }

    public static Task<Void> updatePhotoUrl(String photoUrl){
        return  db.collection(collectionName).document(mAuth.getCurrentUser().getEmail())
            .update("photoUrl" , photoUrl);
    }

}
