package com.example.bloodmatch.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bloodmatch.R;
import com.example.bloodmatch.RequestListActivity;
import com.example.bloodmatch.data.DonorCollection;
import com.example.bloodmatch.model.DonorModel;
import com.example.bloodmatch.model.RequestModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firestore.v1.UpdateDocumentRequestOrBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.RequestViewAdapter> {

    private List<RequestModel> requests;
    private Context context;

    public RequestListAdapter(Context context, List<RequestModel> requests){
        this.requests = requests;
        this.context = context;
    }
    
    public class RequestViewAdapter extends RecyclerView.ViewHolder{

        ImageView ivrecipient;
        TextView tvname, tvaddress, tvblood, tvtime;

        public RequestViewAdapter(final View view) {
            super(view);
            ivrecipient = view.findViewById(R.id.recipient_image);
            tvname = view.findViewById(R.id.recipient_name);
            tvaddress = view.findViewById(R.id.address);
            tvblood = view.findViewById(R.id.requested_blood);
            tvtime = view.findViewById(R.id.time);
        }
    }

    @NonNull
    @Override
    public RequestListAdapter.RequestViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.custom_request_item, parent, false);
        return new RequestViewAdapter(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestListAdapter.RequestViewAdapter holder, int position) {
        RequestModel request = requests.get(position);

        holder.tvaddress.setText(request.getLocation());
        holder.tvblood.setText((request.getBlood().toString()));
        // formatting timestamp
        DateFormat df = new SimpleDateFormat("HH:mm");
        long milliseconds = request.getCreatedAt().getTime();
        String time = df.format(new Date(milliseconds));

        holder.tvtime.setText(time);

        DonorCollection.selectDocumentByRefrence(request.getInNeed())
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        DonorModel recipient = documentSnapshot.toObject(DonorModel.class);
                        displayImage(holder, Uri.parse(recipient.getPhotoUrl()));
                        holder.tvname.setText(recipient.getDisplayName());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    /**
     * Display the image to view element
     */
    private void displayImage(RequestViewAdapter holder, Uri imgUri){
        if(imgUri !=null){
            Glide.with(holder.itemView)
                    .load(imgUri)
                    .into(holder.ivrecipient);
        }
    }

}