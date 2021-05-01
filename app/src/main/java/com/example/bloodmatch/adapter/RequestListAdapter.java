package com.example.bloodmatch;

import android.widget.ArrayAdapter;
import android.content.Context;


import com.google.firebase.auth.FirebaseAuth;

public class RequestListAdapter extends ArrayAdapter<RequestModel> {

    private Context context;
    private List<RequestModel> requests;


    public RequestListAdapter(Context context, List<RequestModel> requests){
        spuer(context, R.layout.custom_request_item, );
        this.requests = requests;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_request_item, null, false);
        TextView tvDispalyName,tvAddress, tvBlood, tvTime;
        ImageView ivrecipient;

        tvDispalyName = view.findById(R.id.recipient_name);
        tvAddress = view.findById(R.id.address);
        tvTime = view.findById(R.id.time);
        tvBlood = view.findById(R.id.requested_blood);
        ivrecipient = view.findById(R.id.recipient_image);

        // Set values here

        return view;
    }
}