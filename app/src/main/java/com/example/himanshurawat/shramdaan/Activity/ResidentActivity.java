package com.example.himanshurawat.shramdaan.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.himanshurawat.shramdaan.R;

public class ResidentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident);
        Button residentCommunityButton=findViewById(R.id.activity_resident_community_button);
        Button residentMCDButton=findViewById(R.id.activity_resident_mcd_button);
        residentCommunityButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ResidentActivity.this,CommunityActivity.class);
                startActivity(i);

            }
        });







    }
}
