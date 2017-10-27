package com.example.himanshurawat.shramdaan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Daksh Garg on 10/27/2017.
 */

public class NearByEventAdapter extends RecyclerView.Adapter<NearByEventAdapter.NearByEventHolder> {
    Context context;
    public NearByEventAdapter(Context context){
        this.context=context;
    }


    @Override
    public NearByEventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_near_by_event,parent,false);
        return new NearByEventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NearByEventHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class NearByEventHolder extends RecyclerView.ViewHolder{
        TextView distanceTextView;
        TextView nameTextView;
        TextView locationTextView;
        TextView dateTimeTextView;
        Button interestedButton;

        public NearByEventHolder(View itemView) {
            super(itemView);
            distanceTextView = itemView.findViewById(R.id.distance_textView);
            nameTextView=itemView.findViewById(R.id.name_textView);
            locationTextView=itemView.findViewById(R.id.location_textView);
            dateTimeTextView=itemView.findViewById(R.id.dateTime_textView);
            interestedButton=itemView.findViewById(R.id.interested_button);
        }
    }
}
