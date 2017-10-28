package com.example.himanshurawat.shramdaan.PojoClass;

import java.io.Serializable;

/**
 * Created by Daksh Garg on 10/28/2017.
 */

public class Distance extends Event implements Serializable {
    public String distance;

    public Distance(String name, String id, String imageUrl, String lat, String lon, String location, String date, String time, String type) {
        super(name, id, imageUrl, lat, lon, location, date, time, type);
    }
}
