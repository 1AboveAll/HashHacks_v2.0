package com.example.himanshurawat.shramdaan.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.himanshurawat.shramdaan.PojoClass.Distance;
import com.example.himanshurawat.shramdaan.PojoClass.NameGenerator;
import com.example.himanshurawat.shramdaan.R;

public class ViewEventActivity extends AppCompatActivity {


    TextView nameTextView,byTextView,locationTextView,dateTextView,timeTextView;
    ImageView eventImageView;
    Distance distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        nameTextView= findViewById(R.id.activity_view_event_name_text_view);
        byTextView= findViewById(R.id.activity_view_event_by_text_view);
        locationTextView= findViewById(R.id.activity_view_event_location_text_view);
        dateTextView= findViewById(R.id.activity_view_event_date_text_view);
        timeTextView= findViewById(R.id.activity_view_event_time_text_view);
        eventImageView= findViewById(R.id.activity_view_event_event_image_view);
        Intent i=getIntent();
        distance=(Distance)i.getSerializableExtra("distance");
        nameTextView.setText(distance.getName());
        byTextView.setText(NameGenerator.oneName);
        timeTextView.setText(distance.getTime());
        dateTextView.setText(distance.getDate());
        locationTextView.setText(distance.getLocation());
        Glide.with(getApplicationContext()).load(distance.getImageUrl()).into(eventImageView);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_event_acitivity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.view_event_activity_contributor_menu){
            Intent it=new Intent(ViewEventActivity.this,ViewContributor.class);
            it.putExtra("id",distance.getId());
            startActivity(it);

        }

        return true;
    }
}
