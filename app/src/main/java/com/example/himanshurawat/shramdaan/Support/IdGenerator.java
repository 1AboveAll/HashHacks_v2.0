package com.example.himanshurawat.shramdaan.Support;


import java.util.UUID;

public class IdGenerator {

    public String uniqueID;

    public IdGenerator(){
        uniqueID="";
    }
    public String getUniqueID(){
        String arr[]= UUID.randomUUID().toString().split("-");
        for(String id: arr){
            uniqueID += id;
        }
        return uniqueID;
    }


}
