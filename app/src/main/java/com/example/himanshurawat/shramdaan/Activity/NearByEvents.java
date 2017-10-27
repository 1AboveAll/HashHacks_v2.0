package com.example.himanshurawat.shramdaan.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.himanshurawat.shramdaan.R;

public class NearByEvents extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_events);
        recyclerView  = findViewById(R.id.near_by_events_recycler_view);

    }
}
