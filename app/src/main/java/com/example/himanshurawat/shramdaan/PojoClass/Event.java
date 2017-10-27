package com.example.himanshurawat.shramdaan.PojoClass;


import com.example.himanshurawat.shramdaan.Support.DatabaseUtility;

import java.io.Serializable;
import java.util.List;

public class Event implements Serializable{

    private String name;
    private String id;
    private String imageUrl;
    private String lat;
    private String lon;
    private String location;
    private String dateTime;

    public Event(){

    }

    public Event(String name, String id, String imageUrl, String lat, String lon, String location, String dateTime) {
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lon = lon;
        this.location = location;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
