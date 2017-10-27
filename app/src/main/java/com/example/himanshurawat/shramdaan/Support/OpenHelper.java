package com.example.himanshurawat.shramdaan.Support;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daksh Garg on 10/27/2017.
 */

public class OpenHelper extends SQLiteOpenHelper {
    //USER TABLE
    public static final String USER_TABLE_NAME = "user";
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String IMAGE_URL = "imageUrl";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String LOCATION = "location";
    public static final String DATE_TIME = "dateTime";


    public OpenHelper(Context context) {
        super(context,"Health.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String table = "CREATE TABLE "+USER_TABLE_NAME+" ( "+NAME+" text, "+ID+" text, "+IMAGE_URL+" text, "+LATITUDE
                +" text, "+LONGITUDE+" text, "+LOCATION+" text, "+DATE_TIME+" text);";
        sqLiteDatabase.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}