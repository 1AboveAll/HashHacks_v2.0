package com.example.himanshurawat.shramdaan.PojoClass;

import java.util.Random;

/**
 * Created by Daksh Garg on 10/28/2017.
 */

public class NameGenerator {

    public static String oneName;

    public static void getRandomName(){
        Random random = new Random();
        int x = random.nextInt(7);
        String names[]={"Himanshu","Akash","Daksh","Deepti","Ayushi","Shivam","Priya"};
        oneName =  names[x];
    }


}
