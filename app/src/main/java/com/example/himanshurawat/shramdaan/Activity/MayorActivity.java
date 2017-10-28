package com.example.himanshurawat.shramdaan.Activity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.himanshurawat.shramdaan.NearByEventAdapter;
import com.example.himanshurawat.shramdaan.PojoClass.Distance;
import com.example.himanshurawat.shramdaan.PojoClass.DistanceSort;
import com.example.himanshurawat.shramdaan.PojoClass.Event;
import com.example.himanshurawat.shramdaan.R;
import com.example.himanshurawat.shramdaan.Support.DatabaseUtility;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MayorActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_mayor);

        recyclerView  = findViewById(R.id.activity_mayor_recycler_view);
        arrayList = new ArrayList<>();
        adapter=new NearByEventAdapter(this, arrayList, new NearByEventAdapter.onButtonClickedInterface() {
            @Override
            public void onButtonClicked(View view, int position) {

            }

            @Override
            public void onEventClicked(View view, int position) {

            }
        });
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
                    Toast.makeText(MayorActivity.this,
                            "Lat: " + location.getLatitude() + " Long: " + location.getLongitude()
                            , Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MayorActivity.this,"No location found",Toast.LENGTH_SHORT).show();
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(MayorActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MayorActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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
                    arrayList.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Event event = snapshot.getValue(Event.class);
                        if(event.getType().equals("Mayor")){
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
}
