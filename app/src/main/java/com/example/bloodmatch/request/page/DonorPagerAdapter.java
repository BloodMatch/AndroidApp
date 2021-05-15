package com.example.bloodmatch.request.page;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.bloodmatch.DonorProfileActivity;
import com.example.bloodmatch.R;
import com.example.bloodmatch.model.DonorModel;
import com.example.bloodmatch.request.RequestManagerActivity;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonorPagerAdapter extends PagerAdapter {
    Context context;
    List<DonorModel> donors;
    LayoutInflater mLayoutInflater;

    public DonorPagerAdapter(Context  context, List<DonorModel> donors){
        this.context = context;
        this.donors = donors;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return donors.size();
    }

    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((FrameLayout) object);
    }

    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the item.xml
        View pageView = mLayoutInflater.inflate(R.layout.page_donors, container,false);

        DonorModel donor = donors.get(position);

        TextView displayNameTextView = pageView.findViewById(R.id.displayName);
        TextView donationsTextView = pageView.findViewById(R.id.donations);
        TextView unitsTextView = pageView.findViewById(R.id.units);
        TextView bloodTextView = pageView.findViewById(R.id.blood);
        ImageView pictureCircleImageView = pageView.findViewById(R.id.picture);

        displayNameTextView.setText(donor.getDisplayName() );
        donationsTextView.setText(String.valueOf( donor.getDonation().getFrequency()));
        unitsTextView.setText(String.valueOf(donor.getDonation().getQuantity()));
        bloodTextView.setText(donor.getBlood().toString());


        Uri imgUri = Uri.parse(donor.getPhotoUrl());
        if( imgUri !=null){
            Glide.with(container.getContext())
                    .load(imgUri)
                    .into(pictureCircleImageView);
        }


        Log.d("adapter page",donor.getDisplayName() );
        // referencing the image view from the item.xml file
        //Objects.requireNonNull(container).addView(pageView);
        ((ViewPager) container).addView(pageView,0);
        return pageView;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
        //return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}
