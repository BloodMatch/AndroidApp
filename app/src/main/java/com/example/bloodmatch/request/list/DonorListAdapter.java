package com.example.bloodmatch.request.list;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bloodmatch.DonorProfileActivity;
import com.example.bloodmatch.R;
import com.example.bloodmatch.model.DonorModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonorListAdapter extends ArrayAdapter<DonorModel> {
    private Activity context;
    private List<DonorModel> donors;

    DonorListAdapter(Activity context, List<DonorModel> donors){
        super(context, R.layout.list_donors, donors);
        this.context = context;
        this.donors = donors;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_donors, null,true);

        DonorModel donor = donors.get(position);

        TextView fullNameText = (TextView) rowView.findViewById(R.id.fullName);
        TextView addressText = (TextView) rowView.findViewById(R.id.address);
        TextView timeText = (TextView) rowView.findViewById(R.id.time);
        TextView bloodGroupText = (TextView) rowView.findViewById(R.id.blood_group);
        CircleImageView donorPicture = (CircleImageView) rowView.findViewById(R.id.donor_picture);

        Uri imgUri = Uri.parse(donor.getPhotoUrl());
        if( imgUri !=null){
            Glide.with(parent.getContext())
                    .load(imgUri)
                    .into(donorPicture);
        }

        fullNameText.setText(donor.getDisplayName());
        addressText.setText(donor.getAddress());
        bloodGroupText.setText(donor.getBlood().toString());

        return rowView;
    }
}
