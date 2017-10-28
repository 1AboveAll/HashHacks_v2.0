package com.example.himanshurawat.shramdaan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.himanshurawat.shramdaan.Activity.CommunityActivity;
import com.example.himanshurawat.shramdaan.Activity.MayorActivity;
import com.example.himanshurawat.shramdaan.Activity.ResidentActivity;
import com.example.himanshurawat.shramdaan.PojoClass.NameGenerator;

public class LoginActivity extends AppCompatActivity {

    Button residentLoginButton,associationLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        residentLoginButton=findViewById(R.id.activity_login_resident_button);
        associationLoginButton=findViewById(R.id.activity_login_association_button);

        if(NameGenerator.oneName==null){
          NameGenerator.getRandomName();
        }

        residentLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, CommunityActivity.class));
            }
        });

        associationLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(LoginActivity.this,MayorActivity.class);
                startActivity(i);

            }
        });



    }
}
