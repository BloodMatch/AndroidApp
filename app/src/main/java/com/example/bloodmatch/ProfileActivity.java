package com.example.bloodmatch;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.bloodmatch.data.DonorCollection;
import com.example.bloodmatch.data.UserAccount;
import com.example.bloodmatch.model.DonorModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ProfileActivity extends BaseActivity implements PopupMenu.OnMenuItemClickListener {

    private FirebaseAuth mAuth;
    private FrameLayout frame;
    private ImageView ivUser;
    private Uri imageUri;
    private Button buttonphoto;
    private TextView tvUserName, tvdonation, tvunit, tvboold;
    private View.OnClickListener takePhoto, chosePhoto;
    private int REQUEST_CODE_FOR_IMAGE = 1000;
    private int REQUEST_FOR_CONTENT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        checkLogin(user);

        buttonphoto = findViewById(R.id.photo);
        registerForContextMenu(buttonphoto);
        ivUser = findViewById(R.id.user_image);
        frame = findViewById(R.id.fl_fragment);
        tvUserName = findViewById(R.id.user_name);
        tvdonation = findViewById(R.id.tvdonation);
        tvunit = findViewById(R.id.tvunit);
        tvboold = findViewById(R.id.tvblood);

        tvUserName.setText(user.getDisplayName());
        displayImage(user.getPhotoUrl());

        // Get user Document
        DonorCollection.selectDocument(user.getEmail())
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        DonorModel donor = documentSnapshot.toObject(DonorModel.class);
                        tvdonation.setText(String.valueOf( donor.getDonation().getFrequency().toString()));
                        tvboold.setText(donor.getBlood().toString());
                        tvunit.setText(String.valueOf(donor.getDonation().getQuantity().toString()));
                        loadFragment(donor);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // For taking picture with phone camera
        if(requestCode == REQUEST_CODE_FOR_IMAGE){
            switch (resultCode){
                case RESULT_OK :
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ivUser.setImageBitmap(bitmap);
                    handelUpload(bitmap);
                    break; // user taked the photo
                case RESULT_CANCELED : break; // user canceled the photo
                default : break;
            }
        }
        // For choosing picture from gallery
        if(requestCode == REQUEST_FOR_CONTENT && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            displayImage(imageUri);
            uploadPhoto();
        }
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_profile;
    }

    /**
     * Load fragment into the frame
     * */
    private void loadFragment(DonorModel donor){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        DisplayProfileFragment displayFragment = new DisplayProfileFragment();
        displayFragment.setDonor(donor);
        fragmentTransaction.replace(R.id.fl_fragment, displayFragment, "displayProfile");
        fragmentTransaction.commit();
    }

    /**
     * Display the image to view element
     */
    private void displayImage(Uri imgUri){
        if(imgUri !=null){
            Glide.with(this)
                    .load(imgUri)
                    .into(ivUser);
        }
    }

    /**
     * ChoosePicture from gallery
     */
    private void ChoosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_FOR_CONTENT);
    }

    /**
     * Upload user photo from local file
     */
    private void uploadPhoto(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image ...");
        pd.show();

        UserAccount.uploadPhoto(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            UserAccount.updatePhotoUrl(imageUri)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(ProfileActivity.this, "Image Updated.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            pd.dismiss();
                            Toast.makeText(ProfileActivity.this, "Faild to upload !", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            pd.setMessage("Upload is " + String.format("%,.2f", progress) + "% done");
                        }
                    });
    }

     /**
     * Upload user photo from data in memory
     */
    private void handelUpload(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        UserAccount.uploadPhoto(baos)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        String imageRef = user.getUid()+".jpeg";
                        Toast.makeText(ProfileActivity.this, "Image taken.", Toast.LENGTH_SHORT).show();
                        UserAccount.getPhotoUrl(imageRef)
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        UserAccount.updatePhotoUrl(uri)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(ProfileActivity.this, "Image Updated.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(ProfileActivity.this, "Faild to upload !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.take_photo);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId() == R.id.camera){
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(i.resolveActivity(getPackageManager()) != null){
                startActivityForResult(i, REQUEST_CODE_FOR_IMAGE);
            }
        }else if(item.getItemId() == R.id.gallery){
            ChoosePicture();
        }
        return true;
    }
}