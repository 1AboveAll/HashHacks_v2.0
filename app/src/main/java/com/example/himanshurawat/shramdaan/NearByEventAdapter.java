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

import com.example.himanshurawat.shramdaan.PojoClass.Distance;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Daksh Garg on 10/27/2017.
 */

public class NearByEventAdapter extends RecyclerView.Adapter<NearByEventAdapter.NearByEventHolder> {
    Context context;
    ArrayList<Distance> arrayList;
    onButtonClickedInterface listener;
    public NearByEventAdapter(Context context,ArrayList<Distance> arrayList,onButtonClickedInterface listener){
        this.context=context;
        this.arrayList=arrayList;
        this.listener=listener;
    }


    @Override
    public NearByEventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_near_by_event,parent,false);
        return new NearByEventHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(NearByEventHolder holder, int position) {
        double distance = Double.valueOf(arrayList.get(position).distance);
        DecimalFormat df = new DecimalFormat("0.0000");
        distance =Double.valueOf(df.format(distance))*1000;
        holder.distanceTextView.setText(distance+" m");
        holder.nameTextView.setText(arrayList.get(position).getName());
        holder.locationTextView.setText(arrayList.get(position).getLocation());
        holder.dateTimeTextView.setText(arrayList.get(position).getDate()+", "+arrayList.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class NearByEventHolder extends RecyclerView.ViewHolder{
        TextView distanceTextView;
        TextView nameTextView;
        TextView locationTextView;
        TextView dateTimeTextView;
        Button interestedButton;
        onButtonClickedInterface listener;

        public NearByEventHolder(View itemView, final onButtonClickedInterface listener) {
            super(itemView);
            distanceTextView = itemView.findViewById(R.id.distance_textView);
            nameTextView=itemView.findViewById(R.id.name_textView);
            locationTextView=itemView.findViewById(R.id.location_textView);
            dateTimeTextView=itemView.findViewById(R.id.dateTime_textView);
            interestedButton=itemView.findViewById(R.id.interested_button);
            this.listener=listener;
            interestedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onButtonClicked(view,getAdapterPosition());
                }
            });
        }
    }
    public interface onButtonClickedInterface{
        public void onButtonClicked(View view,int position);
    }
}
