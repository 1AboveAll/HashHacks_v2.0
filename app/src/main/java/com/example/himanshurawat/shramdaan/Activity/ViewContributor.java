package com.example.himanshurawat.shramdaan.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.himanshurawat.shramdaan.R;
import com.example.himanshurawat.shramdaan.Support.DatabaseUtility;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewContributor extends AppCompatActivity {

    DatabaseReference databaseReference;

    ListView listView;
    ArrayList<String> stringArrayList;
    ArrayAdapter<String> arrayAdapter;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contributor);
        Intent i=getIntent();
        id=i.getStringExtra("id");

        setTitle("Contributors");

        listView=findViewById(R.id.activity_view_contributor_list_view);
        stringArrayList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stringArrayList);
        listView.setAdapter(arrayAdapter);

        databaseReference= DatabaseUtility.getDatabase().getReference();

        databaseReference.child("contributor").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0){
                    for(DataSnapshot child:dataSnapshot.getChildren()){
                        stringArrayList.add(child.getKey());
                        Log.i("Hey",child.getKey()+"1");
                    }
                    arrayAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
