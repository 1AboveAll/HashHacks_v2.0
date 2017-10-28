package com.example.himanshurawat.shramdaan.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.himanshurawat.shramdaan.NearByEventAdapter;
import com.example.himanshurawat.shramdaan.PojoClass.Distance;
import com.example.himanshurawat.shramdaan.PojoClass.DistanceSort;
import com.example.himanshurawat.shramdaan.PojoClass.Event;
import com.example.himanshurawat.shramdaan.PojoClass.NameGenerator;
import com.example.himanshurawat.shramdaan.R;
import com.example.himanshurawat.shramdaan.Support.DatabaseUtility;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NearByEvents extends AppCompatActivity implements NearByEventAdapter.onButtonClickedInterface {
    private RecyclerView recyclerView;
    private ArrayList<Distance> arrayList;
    FusedLocationProviderClient mFusedLocationProviderClient;

    LocationRequest mLocationRequest;

    LocationCallback mLocationCallback;
    Double latMain,lngMain;
    NearByEventAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_events);
        recyclerView  = findViewById(R.id.near_by_events_recycler_view);
        arrayList = new ArrayList<>();
        adapter=new NearByEventAdapter(this,arrayList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        mLocationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    Toast.makeText(NearByEvents.this,
                            "Lat: " + location.getLatitude() + " Long: " + location.getLongitude()
                            , Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(NearByEvents.this,"No location found",Toast.LENGTH_SHORT).show();
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(NearByEvents.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NearByEvents.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latMain=location.getLatitude();
                            lngMain=location.getLongitude();
                            Log.d("Hey",latMain+", "+lngMain);

                            fetchEventsFromFirebase();
                        }
                    }
                });


    }




    private void fetchEventsFromFirebase() {
        DatabaseUtility.getDatabase().getReference().child("events")/*.orderByChild("type").
                equalTo("Community")*/.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Event event = snapshot.getValue(Event.class);
                        if(event.getType().equals("Community")){
                            Distance distance=new Distance(event.getName(),event.getId(),event.getImageUrl(),event.getLat(),
                                   event.getLng(),event.getLocation(),event.getDate(),event.getTime(),event.getType() );
                            arrayList.add(distance);
                        }

                    }
                    for(Distance d : arrayList){
                        double lat = Double.parseDouble(d.getLat());
                        double lng = Double.parseDouble(d.getLng());
                        d.distance = String.valueOf(distance(lat,lng ,latMain,lngMain));

                    }
                    Collections.sort(arrayList , new DistanceSort());
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return Math.abs(dist);
    }


    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }


    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    @Override
    public void onButtonClicked(final View view, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to be part of this event?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(NameGenerator.oneName==null){
                    NameGenerator.getRandomName();
                }
                DatabaseUtility.getDatabase().getReference().child("contributor").child(arrayList.get(position).getId())
                        .child(NameGenerator.oneName).setValue(true).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                view.setBackgroundResource(R.color.colorPrimaryDark);
                            }
                        });


            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onEventClicked(View view, int position) {
        Intent i=new Intent(NearByEvents.this,ViewEventActivity.class);
        i.putExtra("distance",arrayList.get(position));
        startActivity(i);
    }
}
