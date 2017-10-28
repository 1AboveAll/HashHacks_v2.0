package com.example.himanshurawat.shramdaan.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Path;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.himanshurawat.shramdaan.Manifest;
import com.example.himanshurawat.shramdaan.PojoClass.Event;
import com.example.himanshurawat.shramdaan.R;
import com.example.himanshurawat.shramdaan.Support.DatabaseUtility;
import com.example.himanshurawat.shramdaan.Support.IdGenerator;
import com.example.himanshurawat.shramdaan.Support.OpenHelper;
import com.example.himanshurawat.shramdaan.Support.StorageUtility;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity  {


    EditText nameTextView;
    EditText locationTextView;
    EditText dateTextView;
    EditText timeTextView;
    Button setImageButton;
    ImageView imageView;
    Button submitButton;


    //////
    long date;
    long time;
    String dateString="",timeString="";
    String loc="";
    String nameOfEvent="";
    String imageUrl="";
    String lat="",lng="";

    FusedLocationProviderClient mFusedLocationProviderClient;

    LocationRequest mLocationRequest;

    LocationCallback mLocationCallback;

    private String id;

    Uri selectedimg;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        nameTextView = findViewById(R.id.activity_add_event_name_editText);
        locationTextView = findViewById(R.id.activity_add_event_location_editText);
        locationTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(AddEventActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddEventActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mFusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    lat=String.valueOf(location.getLatitude());
                                    lng=String.valueOf(location.getLongitude());
                                    loc=location.toString();
                                   locationTextView.setText(location.toString());
                                }
                            }
                        });
            }
        });
        dateTextView = findViewById(R.id.activity_add_event_date_editText);
        timeTextView = findViewById(R.id.activity_add_event_time_editText);
        setImageButton=findViewById(R.id.activity_add_event_addimage_button);
        imageView=findViewById(R.id.activity_add_event_imageView);


        setImageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose Picture"), 1);
            }
        });



        dateTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                int month = newCalendar.get(Calendar.MONTH);  // Current month
                int year = newCalendar.get(Calendar.YEAR);   // Current year
                showDatePicker(AddEventActivity.this, year, month, 1);
            }
        });
        timeTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTextView.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                time = (long) (3600 * hour + minute * 60) + 19800;

            }
        });

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
                    Toast.makeText(AddEventActivity.this,
                            "Lat: " + location.getLatitude() + " Long: " + location.getLongitude()
                            , Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AddEventActivity.this,"No location found",Toast.LENGTH_SHORT).show();
                }
            }
        };
        submitButton=findViewById(R.id.activity_add_event_submit_button);
        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference databaseReference= DatabaseUtility.getDatabase().getReference();

                id=new IdGenerator().getUniqueID();
                nameOfEvent=nameTextView.getText().toString();
                dateString=dateTextView.getText().toString();
                timeString=timeTextView.getText().toString();
               // imageUrl=task.getResult().getDownloadUrl().toString();
               /* Event newEvent=new Event(nameOfEvent,id,imageUrl,lat,lng,loc,dateString,timeString);
                databaseReference.child("events").push().setValue(newEvent);*/

                storageReference = StorageUtility.getFirebaseStorageReference().getReference().child("events").
                        child(id);
                storageReference.putFile(selectedimg).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        imageUrl=task.getResult().getDownloadUrl().toString();

                        Event newEvent=new Event(nameOfEvent,id,imageUrl,lat,lng,loc,dateString,timeString,"Community");
                        databaseReference.child("events").push().setValue(newEvent);
                        OpenHelper openHelper = new OpenHelper(AddEventActivity.this);
                        SQLiteDatabase database = openHelper.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put(OpenHelper.ID,id);
                        cv.put(OpenHelper.IMAGE_URL,imageUrl);
                        cv.put(OpenHelper.LATITUDE,lat);
                        cv.put(OpenHelper.LONGITUDE,lng);
                        cv.put(OpenHelper.DATE,dateString);
                        cv.put(OpenHelper.TIME,timeString);
                        cv.put(OpenHelper.NAME,nameOfEvent);
                        cv.put(OpenHelper.LOCATION,loc);
                        cv.put(OpenHelper.TYPE,"Community");   ///Mention Type
                        database.insert(OpenHelper.USER_TABLE_NAME,null,cv);


                    }
                });

            }
        });
    }




    public void showDatePicker(Context context, int initialYear, int initialMonth, int initialDay) {

        // Creating datePicker dialog object
        // It requires context and listener that is used when a date is selected by the user.

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    //This method is called when the user has finished selecting a date.
                    // Arguments passed are selected year, month and day
                    @Override
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {

                        // To get epoch, You can store this date(in epoch) in database
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        date = calendar.getTime().getTime();
                        int hours = calendar.get(Calendar.HOUR_OF_DAY);
                        int minutes = calendar.get(Calendar.MINUTE);
                        date = date - (hours * 3600) - (minutes * 60);

                        // Setting date selected in the edit text
                        dateTextView.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, initialYear, initialMonth, initialDay);

        //Call show() to simply show the dialog
        datePickerDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            selectedimg = data.getData();
            Glide.with(this).load(selectedimg).into(imageView);



        }
    }
}


