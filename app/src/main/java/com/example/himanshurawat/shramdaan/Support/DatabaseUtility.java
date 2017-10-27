package com.example.himanshurawat.shramdaan.Support;


import com.google.firebase.database.FirebaseDatabase;

public class DatabaseUtility {


    static FirebaseDatabase database;

    public static FirebaseDatabase getDatabase(){

        if(database==null){
            database=FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }

        return database;
    }


}
