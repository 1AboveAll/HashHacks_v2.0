package com.example.himanshurawat.shramdaan.PojoClass;


import com.example.himanshurawat.shramdaan.Support.DatabaseUtility;

import java.io.Serializable;
import java.util.List;

public class Event implements Serializable{

    private String name;
    private String id;
    private String imageUrl;
    private String lat;
    private String lng;
    private String location;
    private String date;
    private String type;
    private String time;

    public Event(String name, String id, String imageUrl, String lat, String lon, String location, String date, String time
    ,String type) {
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lng = lon;
        this.location = location;
        this.date = date;
        this.time = time;
        this.type=type;
    }
    public Event(){

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

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type=type;
    }
}
